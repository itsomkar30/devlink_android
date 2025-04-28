package com.devlink.app.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devlink.app.authentication.RetrofitClient.apiService
import com.devlink.app.user_feed.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    init {
        Socket.initSocket()
        Log.d("ChatViewModel", "Socket initialized: ${Socket.isSocketInitialized()}")
        Socket.debugSocketStatus()

        Socket.onMessageReceived { sender, message ->
            Log.i("ChatViewModel", "Message received from $sender: \"$message\"")
            _messages.update { old -> old + ChatMessage(sender, message) }
        }
    }

    fun joinChat(firstName: String, userId: String, targetUserId: String) {
        Log.i("ChatViewModel", "$firstName (ID: $userId) joining chat with user ID: $targetUserId")
        try {
            Socket.emitJoinChat(firstName, userId, targetUserId)
        } catch (e: Exception) {
            Log.e("ChatViewModel", "Error joining chat: ${e.message}", e)
        }
    }

    fun sendMessage(
        firstName: String,
        lastName: String,
        userId: String,
        targetUserId: String,
        message: String
    ) {
        Log.i("ChatViewModel", "Sending message from $firstName $lastName (ID: $userId) to $targetUserId: \"$message\"")
        try {
            Socket.emitSendMessage(firstName, lastName, userId, targetUserId, message)
            _messages.update { old -> old + ChatMessage(senderName = firstName, text = message) }
        } catch (e: Exception) {
            Log.e("ChatViewModel", "Error sending message: ${e.message}", e)
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("ChatViewModel", "ViewModel cleared. Disconnecting socket...")
        Socket.disconnect()
    }

    private val _connections = MutableStateFlow<List<User>>(emptyList())
    val connections: StateFlow<List<User>> = _connections

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchConnections(token: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getMessageUsers(token)
                if (response.isSuccessful) {
                    val data = response.body()
                    Log.i("ConnectionResponse", "Success: $data")
                    _connections.value = data ?: emptyList()
                } else {
                    Log.i("ConnectionResponse", "Error code: ${response.code()}")
                    _errorMessage.value = "Error: ${response.code()}"
                }
            } catch (e: Exception) {
                Log.i("ConnectionResponse", "Exception: ${e.message}")
                _errorMessage.value = "Exception: ${e.message}"
            }
        }
    }

    fun fetchChats(token: String, toUserId: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getChats(token, toUserId)
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.i("ChatResponse", "Chats: ${it.chats}")
                        // Convert Chat objects to ChatMessage objects
                        _messages.value = it.chats.map { chat ->
                            ChatMessage(
                                senderName = chat.senderId, // or any mapping logic that suits your data model
                                text = chat.text
                            )
                        }
                    } ?: Log.i("ChatResponse", "Empty response body")
                } else {
                    Log.i("ChatResponse", "Error: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                Log.i("ChatResponse", "Exception fetching chats: ${e.message}")
            }
        }
    }


}
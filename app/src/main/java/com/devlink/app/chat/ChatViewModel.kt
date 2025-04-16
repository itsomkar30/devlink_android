package com.devlink.app.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

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
}

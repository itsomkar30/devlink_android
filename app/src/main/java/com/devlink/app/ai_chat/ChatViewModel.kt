package com.devlink.app.ai_chat

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    val messageList = mutableStateListOf<MessageModel>() // Avoid using lazy for state lists

    // Initialize generativeModel lazily to prevent crash on launch
    private val generativeModel by lazy {
        GenerativeModel(
            modelName = "gemini-2.0-flash",
            apiKey = ApiKey.apiKey
        )
    }

    fun sendMessage(question: String) {
        viewModelScope.launch {
            try {
                Log.d("AIChat", "sendMessage() called with question: $question")

                // Check for empty message
                if (question.isBlank()) {
                    Log.e("AIChat", "Error: Empty message")
                    messageList.add(MessageModel("Error: Message cannot be empty", "model"))
                    return@launch
                }

                // Check if generativeModel is initialized
                if (generativeModel == null) {
                    Log.e("AIChat", "Error: generativeModel is null")
                    messageList.add(MessageModel("Error: AI model not initialized", "model"))
                    return@launch
                }

                Log.d("AIChat", "Initializing chat session...")

                // Start the chat session with history
                val chat = generativeModel.startChat(
//                    history = messageList.map {
//                        content(role = it.role) { text(it.message) }
//                    }
                )

                Log.d("AIChat", "Chat initialized successfully.")

                // Add user question to the message list
                messageList.add(MessageModel(message = question.trim(), role = "user"))

                Log.d("AIChat", "Sending message to AI API...")

                // Send the question to the generative model and handle the response
                val response = chat.sendMessage(question)
                val responseText = response?.text ?: "No response from AI"

                Log.d("AIChat", "Received response: $responseText")

                // Add AI response to the message list
                messageList.add(MessageModel(message = responseText, role = "model"))

            } catch (e: Exception) {
                // Log unexpected errors and add them to the message list
                Log.e("AIChat", "Error: ${e.message}", e)
                messageList.add(MessageModel("Error: ${e.message ?: "Unknown error"}", "model"))
            }
        }
    }




}



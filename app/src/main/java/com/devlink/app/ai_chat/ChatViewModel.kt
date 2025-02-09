package com.devlink.app.ai_chat

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    val messageList by lazy { mutableStateListOf<MessageModel>() }

    val generativeModel: GenerativeModel = GenerativeModel(
//        modelName = "gemini-1.5-pro",
        modelName = "gemini-pro",
        apiKey = ApiKey.apiKey
    )


    fun sendMessage(question: String) {
        viewModelScope.launch {
            try {
                val chat = generativeModel.startChat(
                    history = messageList.map {
                        content(it.role) { text(it.message) }
                    }.toList()
                )
                messageList.add(
                    MessageModel(
                        message = question.trim(),
                        role = "user"
                    )
                )

                val response = chat.sendMessage(question)

                messageList.add(
                    MessageModel(
                        message = response.text.toString(),
                        role = "model"
                    )
                )
            } catch (e: Exception) {
                messageList.add(
                    MessageModel(
                        message = "Error: ${e.message.toString()}",
                        role = "model"
                    )
                )
            }
        }
    }

}
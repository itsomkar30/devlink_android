package com.devlink.app.chat

data class ChatMessage(
    val senderName: String,
    val text: String
)

data class Chat(
    val senderId: String,
    val text: String,
    val _id: String,
    val createdAt: String,
    val updatedAt: String
)

data class ChatResponse(
    val chats: List<Chat>
)


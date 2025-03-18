package com.devlink.app.user_feed


data class ConnectionResponse(
    val message: String,
    val data: ConnectionData
)
data class ConnectionData(
    val fromUserId: String,
    val toUserId: String,
    val status: String,
    val _id: String,
    val createdAt: String,
    val updatedAt: String,
    val __v: Int = 0
)

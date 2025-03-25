package com.devlink.app.connection_status

data class ConnectionReceived(
    val message: String,
    val data: List<ConnectionRequestData>
)

data class ConnectionRequestData(
    val _id: String,
    val fromUserId: FromUser,
    val toUserId: String,
    val status: String,
    val createdAt: String,
    val updatedAt: String,
    val __v: Int = 0
)

data class FromUser(
    val _id: String
)
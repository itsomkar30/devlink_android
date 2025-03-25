package com.devlink.app.connection_status


data class ConnectionSendResponse(
    val message :String,
    val data: ConnectionSendData
)

data class ConnectionSendData(
    val _id: String,
    val fromUserId: String,
    val toUserId: String,
    val status: String,
    val createdAt: String,
    val updatedAt: String,
    val __v: Int = 0
)
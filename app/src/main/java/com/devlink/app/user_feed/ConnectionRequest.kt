package com.devlink.app.user_feed


data class ConnectionRequest(
    val status: String
)

data class ConnectionResponse(
    val message: String,
    val data: Any?
)

package com.devlink.app.connection_status

data class ConnectionReceived(
    val message: String,
    val data: List<ConnectionRequestData>
)

data class ConnectionRequestData(
    val _id: String,
    val fromUserId: FromUser,
    val toUserId: FromUser,
    val status: String,
    val createdAt: String,
    val updatedAt: String,
    val __v: Int = 0
)

data class UserProfile(
    val skills : List<String>,
    val _id: String,
    val firstname: String,
    val lastname: String,
    val email: String,
    val password: String,
    val photoURL: String,
    val createdAt: String,
    val updatedAt: String,
    val __v: Int = 0
)

data class FromUser(
    val skills: List<String>,
    val _id: String,
    val firstname: String,
    val lastname: String,
    val photoURL: String
)
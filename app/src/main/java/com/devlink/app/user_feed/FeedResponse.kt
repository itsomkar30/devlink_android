package com.devlink.app.user_feed

import androidx.lifecycle.LiveData

data class FeedResponse(
    val users: List<User>
)

data class UserData(
    val _id: String,
    val firstname: String,
    val lastname: String,
    val email: String,
    val photoURL: String
)


data class User(
    val skills: List<String>,
    val _id: String,
    val firstname: String,
    val lastname: String,
    val photoURL: String
)

data class UserId(
    val _id: String
)
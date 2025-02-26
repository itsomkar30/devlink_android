package com.devlink.app.user_feed

import androidx.lifecycle.LiveData

data class FeedResponse(
    val data: List<UserData>,
    val users: List<UserId>
)

data class UserData(
    val _id: String,
    val firstname: String,
    val lastname: String,
    val email: String,
    val photoURL: String
)


data class User(
    val id: String,
    val firstname: String,
    val lastname: String,
    val photoURL: String?,
    val age: Int?
)

data class UserId(
    val _id: String
)
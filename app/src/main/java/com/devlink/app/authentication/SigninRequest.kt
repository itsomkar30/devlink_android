package com.devlink.app.authentication

data class SigninRequest(
    val email: String,
    val password: String
)

data class SigninResponse(
    val user: UserModel,
    val token: String?
)

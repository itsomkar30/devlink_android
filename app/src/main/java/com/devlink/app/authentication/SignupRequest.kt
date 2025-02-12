package com.devlink.app.authentication

data class SignupRequest(
    val firstname: String,
    val lastname: String,
    val email: String,
    val password: String
)

data class SignupResponse(val message: String)

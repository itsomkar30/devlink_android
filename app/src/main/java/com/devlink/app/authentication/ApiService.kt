package com.devlink.app.authentication

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("user/signup")
    suspend fun signup(@Body request: SignupRequest): Response<SignupResponse>

    @POST("/user/signin")
    suspend fun signin(@Body request: SigninRequest): Response<SigninResponse>
}

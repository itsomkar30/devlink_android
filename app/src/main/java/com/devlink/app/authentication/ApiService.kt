package com.devlink.app.authentication

import com.devlink.app.user_feed.ConnectionResponse
import com.devlink.app.user_feed.FeedResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {
    @POST("user/signup")
    suspend fun signup(@Body request: SignupRequest): Response<SignupResponse>

    @POST("/user/signin")
    suspend fun signin(@Body request: SigninRequest): Response<SigninResponse>

    @GET("/feed")
    suspend fun user_feed(
        @Query("token") token: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<FeedResponse>

    @GET
    suspend fun getJsonFromUrl(@Url url: String): FeedResponse

    @POST("connection/send/{id}/{status}")
    fun sendConnectionRequest(
        @Path("id") toUserId: String,
        @Path("status") status: String,
        @Query("token") token: String
    ): Response<ConnectionResponse>
}

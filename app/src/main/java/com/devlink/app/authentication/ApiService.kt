package com.devlink.app.authentication

import android.telecom.ConnectionRequest
import com.devlink.app.chat.ChatResponse
import com.devlink.app.connection_status.ConnectionReceived
import com.devlink.app.connection_status.ConnectionSendResponse
import com.devlink.app.connection_status.UserProfile
import com.devlink.app.profile.ImageUploadResponse
import com.devlink.app.profile.SkillRequest
import com.devlink.app.profile.SkillResponse
import com.devlink.app.user_feed.ConnectionResponse
import com.devlink.app.user_feed.FeedResponse
import com.devlink.app.user_feed.User
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {
    @POST("api/user/signup")
    suspend fun signup(@Body request: SignupRequest): Response<SignupResponse>

    @POST("api/user/signin")
    suspend fun signin(@Body request: SigninRequest): Response<SigninResponse>

    @POST("api/user/logout")
    suspend fun logout(
        @Query("token") token: String
    ): Response<LogoutResponse>

    @GET("api/feed")
    suspend fun user_feed(
        @Query("token") token: String,
    ): Response<FeedResponse>

    @GET
    suspend fun getJsonFromUrl(@Url url: String): FeedResponse

    @POST("api/connection/send/{toUserId}/{status}")
    suspend fun sendConnectionRequest(
        @Path("toUserId") toUserId: String,
        @Path("status") status: String,
        @Query("token") token: String
    ): Response<ConnectionResponse>

    @GET("api/user/request/recived")
    suspend fun getReceivedRequests(
        @Query("token") token: String
    ): Response<ConnectionReceived>

    @GET("api/profile/view")
    suspend fun getProfileFromToken(
        @Query("token") token: String
    ): Response<UserProfile>

    @GET("api/profile")
    suspend fun getProfileFromUserId(
        @Query("id") id: String
    ): Response<UserProfile>

    @Multipart
    @POST("upload/{userId}")
    suspend fun uploadImage(
        @Path("userId") userId: String,
        @Part image: MultipartBody.Part
    ): Response<ImageUploadResponse>

    @POST("api/connection/review/{status}/{requestId}")
    suspend fun updateConnectionStatus(
        @Path("status") status: String,
        @Path("requestId") requestId: String,
        @Query("token") token: String
    ): Response<ConnectionSendResponse>

    @POST("api/profile/skill")
    suspend fun updateSkills(
        @Query("token") token: String,
        @Body skill: SkillRequest
    ): Response<SkillResponse>

    @GET("api/feed")
    suspend fun getUsersFromSkill(
        @Query("token") token: String,
        @Query("skills") skills: String
    ): Response<FeedResponse>

    @GET("api/request/get")
    suspend fun getMessageUsers(
        @Query("token") token: String
    ): Response<List<User>>

    @GET("api/user/chat")
    suspend fun getChats(
        @Query("token") token: String,
        @Query("toUserId") toUserId: String
    ): Response<ChatResponse>

}

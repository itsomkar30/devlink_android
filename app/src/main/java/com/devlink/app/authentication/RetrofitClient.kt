package com.devlink.app.authentication
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient

object RetrofitClient {
    private const val BASE_URL = "https://temp-deploye.vercel.app/" // Replace with actual API URL

    private val client = OkHttpClient.Builder().build()

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())  // JSON serialization
            .client(client)
            .build()
            .create(ApiService::class.java)
    }
}

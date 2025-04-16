package com.devlink.app.user_feed

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devlink.app.authentication.RetrofitClient.apiService
import com.devlink.app.authentication.SigninResponse
import com.devlink.app.authentication.UserModel
import com.google.gson.Gson
import kotlinx.coroutines.launch

class FeedModel : ViewModel() {
    var feedData = mutableStateOf<FeedResponse?>(null)
    var feedDataResponse = mutableStateListOf<User>()
    var skillFeedData = mutableStateOf<FeedResponse?>(null)
    var skillFeedDataResponse = mutableStateListOf<User>()


    fun FeedCheck(userModel: UserModel, signinResponse: SigninResponse) {

        val token = signinResponse.token

        viewModelScope.launch {
            try {
                val request = apiService.user_feed(token = token.toString())
                val url = request.raw().request.url.toString()
                Log.i("Response Received", request.toString())
                Log.i("Response URL", url)

                val jsonResponse = apiService.getJsonFromUrl(url)
                feedData.value = jsonResponse
                val jsonData = jsonResponse
                val feedResponse = request.body()

                feedDataResponse.clear()
                feedDataResponse.addAll(feedResponse?.users ?: emptyList())


                Log.i("Response JSON", jsonData.toString())
                Log.i("Response Data JSON", feedDataResponse.toString())

            } catch (e: Exception) {
                Log.i("Error Occurred", e.message.toString())
            }

        }

    }

    fun fetchFeedFromSkill(token: String, skills: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getUsersFromSkill(token, skills)

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    skillFeedData.value = responseBody

                    skillFeedDataResponse.clear()
                    skillFeedDataResponse.addAll(responseBody?.users ?: emptyList())

                    Log.i("Skill Feed Success", responseBody.toString())
                    Log.i("Skill Users List", skillFeedDataResponse.toString())
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("Skill Feed Error", "Code: ${response.code()}, Error: $errorBody")
                }
            } catch (e: Exception) {
                Log.e("Skill Feed Exception", e.message.toString())
            }
        }
    }

//    fun removeUserFromList(user: UserData) {
//        viewModelScope.launch {
//            // Log the current list before attempting to remove the user
//            Log.i("Current User List", feedDataResponse.toString())
//
//            val isRemoved = feedDataResponse.remove(user) // Directly remove the user from the list
//            if (isRemoved) {
//                Log.i("User Removed", "Removed ${user.firstname} successfully!")
//            } else {
//                Log.i("User Remove Failed", "Failed to remove ${user.firstname}. The user might not exist in the list.")
//            }
//            // Log the updated list after attempting the removal
//            Log.i("Updated User List", feedDataResponse.toString())
//        }
//    }

    fun sendConnectionRequest(toUserId: String, status: String, token: String) {
        viewModelScope.launch {
            try {
                val response = apiService.sendConnectionRequest(toUserId, status, token)
                val url = response.raw().request.url.toString()
                Log.i("Connection URL ", url)
                Log.i("Connection Success", response.message())

                if (response.isSuccessful) {
                    val jsonResponse = Gson().toJson(response.body())
                    Log.i("Connection Success", jsonResponse)
                }
            } catch (e: Exception) {
                Log.i("Connection Error", e.message.toString())

            }
        }
    }

}

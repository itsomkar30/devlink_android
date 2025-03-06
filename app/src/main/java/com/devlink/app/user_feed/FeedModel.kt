package com.devlink.app.user_feed

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devlink.app.authentication.RetrofitClient.apiService
import com.devlink.app.authentication.SigninResponse
import com.devlink.app.authentication.UserModel
import kotlinx.coroutines.launch

class FeedModel : ViewModel() {
    var feedData = mutableStateOf<FeedResponse?>(null)

    var feedDataResponse = mutableStateOf<List<UserData>>(emptyList())

    fun FeedCheck(userModel: UserModel, signinResponse: SigninResponse) {

        val token = signinResponse.token
//        val token =
//            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2N2IwN2Q5MGU2Y2M0OGNjY2NjNDE5NzQiLCJpYXQiOjE3NDAyOTkyOTF9.KtQgHZYY1LhZD8LBh0t8AhN00uQ_9QGaNrrzdr3P1XQ"
        viewModelScope.launch {
            try {
                val request = apiService.user_feed(token = token.toString(), page = 1, limit = 10)
                val url = request.raw().request.url.toString()
                Log.i("Response Received", request.toString())
                Log.i("Response URL", url)

                val jsonResponse = apiService.getJsonFromUrl(url)
                feedData.value = jsonResponse
                val jsonData = jsonResponse
                val feedResponse = request.body()
                feedDataResponse.value = feedResponse?.data ?: emptyList()
                Log.i("Response JSON", jsonData.toString())
                Log.i("Response Data JSON", feedDataResponse.toString())

            } catch (e: Exception) {
                Log.i("Error Occurred", e.message.toString())
            }

        }

    }


    fun sendConnectionRequest(toUserId: String, status: String, token: String) {
        viewModelScope.launch {
            try {
                val response = apiService.sendConnectionRequest(toUserId, status, token)
                Log.i("Connection Success", response.message())
            } catch (e: Exception) {
                Log.i("Connection Error", e.message.toString())

            }
        }
    }

}


//class FeedModel : ViewModel() {
//    fun FeedCheck(userModel: UserModel) {
////        val token = userModel.id
//        val token =
//            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2N2IwN2Q5MGU2Y2M0OGNjY2NjNDE5NzQiLCJpYXQiOjE3NDAyOTkyOTF9.KtQgHZYY1LhZD8LBh0t8AhN00uQ_9QGaNrrzdr3P1XQ"
//        viewModelScope.launch {
//            try {
//                val request = apiService.user_feed(token = token, page = 1, limit = 10)
//                val url = request.raw().request.url.toString()
//                Log.i("Response Received", request.toString())
//                Log.i("Response URL", url)
//
//                val jsonResponse = apiService.getJsonFromUrl(url)
//                val jsonData = jsonResponse
//                Log.i("Response JSON", jsonData.toString())
//                Log.i("Response JSON", jsonData.users.toString())
//            } catch (e: Exception) {
//                Log.i("Error Occurred", e.message.toString())
//            }
//
//        }
//
//    }
//}


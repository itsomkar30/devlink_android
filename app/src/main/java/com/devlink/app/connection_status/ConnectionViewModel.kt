package com.devlink.app.connection_status

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devlink.app.authentication.RetrofitClient.apiService
import com.devlink.app.authentication.SigninResponse
import kotlinx.coroutines.launch

class ConnectionViewModel : ViewModel() {
    val connections = mutableStateListOf<ConnectionRequestData>()
    fun getConnectionRequests(signinResponse: SigninResponse) {

        viewModelScope.launch {
            try {
                val apiResponse = apiService.getReceivedRequests(signinResponse.token.toString())
                val response = apiResponse.body()

                response?.let {
                    Log.d("API_RESPONSE", "Message: ${it.message}")
                    // Clear old data and add new data
                    connections.clear()
                    connections.addAll(it.data)

                    it.data.forEach { connection ->
                        Log.d(
                            "API_RESPONSE",
                            "Connection ID: ${connection._id},From User:${connection.fromUserId._id}, Status: ${connection.status}"
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "Error: ${e.message}")
            }
        }
    }


    fun updateConnectionStatus(status: String, requestId: String, token: String) {
        viewModelScope.launch {
            try {
                val response = apiService.updateConnectionStatus(
                    status = status,
                    requestId = requestId,
                    token = token
                )

                if (response.isSuccessful) {
                    Log.i("Request Updated", "Response: ${response.body()?.toString()}")
                } else {
                    Log.e("Request Update Error", "Error Code: ${response.code()}, Error Body: ${response.errorBody()?.string()}")
                }

            } catch (e: Exception) {
                Log.i("Request Update Error", "Response: ${e.message}")
            }
        }
    }
}
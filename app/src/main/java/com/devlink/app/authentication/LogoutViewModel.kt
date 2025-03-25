package com.devlink.app.authentication

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.devlink.app.Screen
import com.devlink.app.authentication.RetrofitClient.apiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LogoutViewModel : ViewModel() {
    var logoutStatus by mutableStateOf("")

    fun logoutUser(context: Context, navController: NavController, token: String) {
        viewModelScope.launch {
            try {
                val response = apiService.logout(token)
                if (response.isSuccessful) {
                    logoutStatus = response.body()?.message ?: "User logged out successfully!"

                    UserPreferences.clearUserData(context)

                    Log.i("Logout", "User logged out successfully. DataStore cleared.")

                    withContext(Dispatchers.Main) {
                        navController.navigate(Screen.login_screen)
                    }

                } else {
                    logoutStatus = response.errorBody()?.string() ?: "Logout failed!"
                }
            } catch (e: Exception) {
                logoutStatus = "Logout failed: ${e.message}"
            }

            Log.i("Logout Status", logoutStatus)

        }
    }
}
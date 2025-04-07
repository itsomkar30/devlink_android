package com.devlink.app.profile

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devlink.app.authentication.RetrofitClient.apiService
import kotlinx.coroutines.launch


class SkillViewModel : ViewModel() {
    var skills = mutableStateOf(listOf<String>())

    fun updateSkills(token: String, newSkills: List<String>) {
        viewModelScope.launch {
            try {
                val request = SkillRequest(newSkills)

                val response = apiService.updateSkills(token, request)

                if (response.isSuccessful) {
                    val message = response.body()?.message
                    skills.value = newSkills
                    Log.d("API", "Success: $message")
                    Log.d("API Resposne", skills.value.toString())
                } else {
                    Log.e("API", "Server error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("API", "Exception: ${e.localizedMessage}")
            }
        }

    }

}
package com.devlink.app.authentication

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object UserPreferences {
    private val TOKEN_KEY = stringPreferencesKey("token")
    private val USER_ID_KEY = stringPreferencesKey("user_id")
    private val EMAIL_KEY = stringPreferencesKey("email")

    suspend fun saveUserData(context: Context, token: String, userId: String, email: String) {
        context.dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
            prefs[USER_ID_KEY] = userId
            prefs[EMAIL_KEY] = email
        }
    }

    suspend fun getUserData(context: Context): Triple<String?, String?, String?> {
        val prefs = context.dataStore.data.first()
        return Triple(prefs[TOKEN_KEY], prefs[USER_ID_KEY], prefs[EMAIL_KEY])
    }

    suspend fun clearUserData(context: Context) {
        Log.i("UserPreferences", "Clearing user data on logout...")
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
        Log.i("UserPreferences", "User data cleared successfully.")
    }
}

package com.devlink.app.user_feed

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class InterestedIgnoredViewModel : ViewModel() {
    val interestedList = mutableStateListOf<User>()
    val ignoredList = mutableStateListOf<User>()

    fun addUserToInterested(user: User) {
        if (!interestedList.contains(user)) {
            interestedList.add(user)
        }
    }

    fun addUserToIgnored(user: User) {
        if (!ignoredList.contains(user)) {
            ignoredList.add(user)
        }
    }
}
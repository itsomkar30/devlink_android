package com.devlink.app.user_feed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun SearchScreenView(navController: NavController, token: String) {
    val feedModel: FeedModel = viewModel()
    val userList = feedModel.skillFeedDataResponse
    LaunchedEffect(Unit) {
        feedModel.fetchFeedFromSkill(token, "kotlin")
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(token)
        if (userList.isEmpty()) {
            Text(text = "No users found or loading...")
        } else {
            userList.forEach { user ->
                Text(text = "${user.firstname} ${user.lastname}")
                Text(text = "Skills: ${user.skills.joinToString()}")
            }
        }
    }
}
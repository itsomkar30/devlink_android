package com.devlink.app.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.devlink.app.connection_status.ConnectionViewModel

@Composable
fun ChatScreenView(token: String) {
    val viewModel: ChatViewModel = viewModel()
    val connectionViewModel: ConnectionViewModel = viewModel()
    val messages by viewModel.messages.collectAsState()
    var input by remember { mutableStateOf("") }
    val targetUserId = "67b07d90e6cc48ccccc41974"

    val user by remember { connectionViewModel.user }
    LaunchedEffect(user) {
        connectionViewModel.fetchProfileFromToken(token)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        ) {
            items(messages) { msg ->
                Text("${msg.senderName}: ${msg.text}", modifier = Modifier.padding(4.dp))
            }
        }


        Row(modifier = Modifier.padding(16.dp)) {
            TextField(
                value = input,
                onValueChange = { input = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Type a message...") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (input.isNotBlank()) {
                    viewModel.sendMessage(
                        firstName = "User",
                        lastName = "Xtreme",
                        userId = user?._id.toString(),
                        targetUserId = targetUserId,
                        message = input
                    )
                    input = ""
                }
            }) {
                Text("Send")
            }
        }
    }

    // Call this once to join chat room
    LaunchedEffect(user?._id) {
        if (user?._id!= null) {
            viewModel.joinChat(
                firstName = "testname",
                userId = user?._id.toString(),
                targetUserId = targetUserId
            )
        }
    }
}

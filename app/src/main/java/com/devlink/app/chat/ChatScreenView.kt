package com.devlink.app.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.devlink.app.R
import com.devlink.app.Screen
import com.devlink.app.authentication.ModifiedTextField
import com.devlink.app.connection_status.ConnectionViewModel
import com.devlink.app.ui.TopBar
import com.devlink.app.user_feed.User

@Composable
fun ChatScreenView(navController: NavController, token: String) {
    val viewModel: ChatViewModel = viewModel()
    val messageUsers by viewModel.connections.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchConnections(token)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBar(75, "Messages", true, navController) },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(R.color.jet_black))
                .padding(paddingValues) // Ensures proper padding
        ) {

            if (messageUsers.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        color = Color.White,
                        strokeWidth = 4.dp
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    items(messageUsers) { user ->
                        MessageUserItem(user, token, navController)
                    }
                }
            }
        }
    }
}

@Composable
fun MessageUserItem(user: User, token: String, navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(85.dp)
            .background(color = colorResource(R.color.black_modified))
            .clickable { navController.navigate(Screen.message_screen + "/$token/${user._id}/${user.firstname} ${user.lastname}") }
            .padding(16.dp)
    ) {

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    model = if (user?.photoURL != "" || user?.photoURL!!.isNotEmpty()) {
                        user?.photoURL.toString()
                    } else {
                        R.raw.default_dp
                    },
                    contentDescription = "User Profile Picture",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "${user?.firstname} ${user?.lastname}",
                    fontFamily = FontFamily(Font(R.font.josefin_sans_bold)),
                    fontSize = 18.sp
                )
            }
        }

    }

}

@Composable
fun MessageScreen(token: String, targetUserId: String, targetUserName: String, navController: NavController) {
    val viewModel: ChatViewModel = viewModel()
    val connectionViewModel: ConnectionViewModel = viewModel()
    val messages by viewModel.messages.collectAsState()
    var input by remember { mutableStateOf("") }
    val targetUserId = targetUserId

    val user by remember { connectionViewModel.user }
    LaunchedEffect(user) {
        connectionViewModel.fetchProfileFromToken(token)
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBar(75, targetUserName, true, navController) },
        containerColor = colorResource(R.color.jet_black)
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = colorResource(R.color.jet_black))
                    .padding(8.dp)
            ) {
                Text(targetUserId)
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp)
                ) {
                    items(messages) { msg ->
                        Text("${msg.senderName}: ${msg.text}", modifier = Modifier.padding(4.dp))
                    }
                }


                Row(
                    modifier = Modifier
                        .fillMaxWidth(),

                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ModifiedTextField(
                        name = input,
                        onNameChange = { input = it },
                        label = "Message",
                        isSingleLine = false,
                        modifier = Modifier
                            .weight(1f) // Prevents shrinking
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    IconButton(
                        onClick = {
                            if (input.isNotBlank()) {
                                viewModel.sendMessage(
                                    firstName = user?.firstname.toString(),
                                    lastName = user?.lastname.toString(),
                                    userId = user?._id.toString(),
                                    targetUserId = targetUserId,
                                    message = input
                                )
                                input = ""
                            }
                        },
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(colorResource(R.color.snap_yellow))
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.Send,
                            contentDescription = "Send",
                            tint = Color.Black
                        )
                    }
                }

            }
        }
    }


    LaunchedEffect(user?._id) {
        if (user?._id != null) {
            viewModel.joinChat(
                firstName = user?._id + targetUserId,
                userId = user?._id.toString(),
                targetUserId = targetUserId
            )
        }
    }
}

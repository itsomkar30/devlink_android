package com.devlink.app.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.filled.Refresh
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.devlink.app.R
import com.devlink.app.Screen
import com.devlink.app.authentication.ModifiedTextField
import com.devlink.app.connection_status.ConnectionViewModel
import com.devlink.app.connection_status.UserProfile
import com.devlink.app.ui.TopBar
import com.devlink.app.user_feed.User
import kotlinx.coroutines.delay

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

            var isTimeout by remember { mutableStateOf(false) }

            LaunchedEffect(messageUsers) {
                if (messageUsers.isEmpty()) {
                    delay(10000) // wait for 10 seconds
                    if (messageUsers.isEmpty()) {
                        isTimeout = true
                    }
                } else {
                    isTimeout = false
                }
            }

            if (messageUsers.isEmpty()) {
                if (isTimeout) {
                    // Timeout occurred, show error
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "No Internet",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Seems like you have no connections to chat",
                            fontFamily = FontFamily(Font(R.font.josefin_sans_bold)),
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Please check your internet connection",
                            fontFamily = FontFamily(Font(R.font.josefin_sans_bold)),
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            color = Color.White,
                            strokeWidth = 4.dp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Loading Users...",
                            fontFamily = FontFamily(Font(R.font.josefin_sans_bold)),
                            color = Color.White
                        )
                    }
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
fun MessageScreen(
    token: String,
    targetUserId: String,
    targetUserName: String,
    navController: NavController
) {
    val viewModel: ChatViewModel = viewModel()
    val connectionViewModel: ConnectionViewModel = viewModel()
    val messages by viewModel.messages.collectAsState()
    var input by remember { mutableStateOf("") }
    val targetUserId = targetUserId


    val user by remember { connectionViewModel.user }
    LaunchedEffect(user) {
        connectionViewModel.fetchProfileFromToken(token)
    }
    LaunchedEffect(token, targetUserId, user) {
        // Ensure that the token is valid and the user profile is loaded before fetching chats.
        if (user != null && token.isNotBlank()) {
            viewModel.fetchChats(token, targetUserId)
        }
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
            ) {
//                Text(targetUserId)
                LazyColumn(
                    modifier = Modifier
                        .weight(1f),
                    reverseLayout = true
                ) {
                    items(messages.reversed()) { msg ->
//                        Text("${msg.senderName}: ${msg.text}", modifier = Modifier.padding(4.dp))
                        MessageRow(message = msg, user = user)
                    }
                }


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),

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
                                    firstName = user?._id.toString(),
                                    lastName = user?.lastname.toString(),
                                    userId = user?._id.toString(),
                                    targetUserId = targetUserId,
                                    message = input.trim()
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
                firstName = user?._id.toString(),
                userId = user?._id.toString(),
                targetUserId = targetUserId
            )
        }
    }
}

@Composable
fun MessageRow(message: ChatMessage, user: UserProfile?) {

    var isSender = message.senderName == user?._id

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .align(
                        if (isSender) Alignment.BottomEnd else Alignment.BottomStart
                    )
                    .padding(
                        start = if (!isSender) 8.dp else 50.dp,
                        end = if (!isSender) 70.dp else 8.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    )
                    .clip(RoundedCornerShape(48f))
                    .background(
                        if (!isSender) colorResource(R.color.black_modified) else colorResource(R.color.white)
                    )
                    .padding(12.dp)
            ) {
                Text(
                    text = message.text,
                    fontFamily = FontFamily(Font(R.font.josefin_sans_bold)),
                    fontWeight = FontWeight(500),
                    color = if (!isSender) colorResource(R.color.white) else
                        colorResource(R.color.black_modified)
                )
            }


        }
    }

}
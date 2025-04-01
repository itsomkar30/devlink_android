package com.devlink.app.ai_chat


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.devlink.app.R
import com.devlink.app.authentication.LottieAnimationLoop
import com.devlink.app.authentication.ModifiedTextField
import com.devlink.app.ui.TopBar
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


//@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun ChatPageView(navController: NavController) {
//    val chatViewModel: ChatViewModel = viewModel()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBar(90, "Gemini AI", true, navController) },
        bottomBar = { Spacer(modifier = Modifier.height(0.dp)) } // Invisible bottom bar
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(paddingValues) // Ensures proper padding
        ) {
            ChatPage()
        }
    }
}

//@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun ChatPage() {
    val messageList = remember { mutableStateListOf<MessageModel>() }
    val coroutineScope = rememberCoroutineScope()

    val generativeModel = remember {
        GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = ApiKey.apiKey
        )
    }

    fun sendMessage(question: String) {
        coroutineScope.launch {
            try {
                val chat = generativeModel.startChat(
                    history = messageList.map {
                        content(it.role) { text(it.message) }
                    }.toList()
                )

                messageList.add(MessageModel(message = question.trim(), role = "user"))

                val response = chat.sendMessage(question)

                messageList.add(MessageModel(message = response.text.orEmpty(), role = "model"))
            } catch (e: Exception) {
                messageList.add(MessageModel(message = "Error: ${e.message}", role = "model"))
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Chat messages
        MessageList(
            modifier = Modifier.weight(1f),
            messageList = messageList
        )

        // Message input bar
        MessageInput(
            modifier = Modifier
                .fillMaxWidth()
        ) {question->
//            viewModel.sendMessage(it)
            sendMessage(question)

        }
    }
}

@Composable
fun MessageInput(modifier: Modifier = Modifier, onMessageSend: (String) -> Unit) {
    var message by remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ModifiedTextField(
                name = message,
                onNameChange = { message = it },
                label = "Message",
                isSingleLine = false,
                modifier = Modifier
                    .weight(1f) // Prevents shrinking
            )

            Spacer(modifier = Modifier.width(10.dp))

            IconButton(
                onClick = {
                    if (message.isNotEmpty()) {
                        onMessageSend(message)
                        message = ""
                    }
                },
                modifier = Modifier
                    .clip(CircleShape)
                    .background(colorResource(R.color.snap_yellow))
            ) {
                Icon(imageVector = Icons.AutoMirrored.Rounded.Send, contentDescription = "Send")
            }
        }
    }
}


@Composable
fun MessageRow(messageModel: MessageModel) {

    val isModel = messageModel.role == "model"

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .align(
                        if (isModel) Alignment.BottomStart else Alignment.BottomEnd
                    )
                    .padding(
                        start = if (isModel) 8.dp else 50.dp,
                        end = if (isModel) 70.dp else 8.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    )
                    .clip(RoundedCornerShape(48f))
                    .background(
                        if (isModel) colorResource(R.color.black_modified) else colorResource(R.color.white)
                    )
                    .padding(12.dp)
            ) {
                Text(
                    text = messageModel.message,
                    fontFamily = FontFamily(Font(R.font.josefin_sans_bold)),
                    fontWeight = FontWeight(500),
                    color = if (isModel) colorResource(R.color.white) else
                        colorResource(R.color.black_modified)
                )
            }


        }
    }

}

@Composable
fun MessageList(modifier: Modifier = Modifier, messageList: List<MessageModel>) {
    if (messageList.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.88f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieAnimationLoop(
                fileName = R.raw.chatscreen_animation,
                size = 300.dp
            )
            Text(
                "Chat with Gemini", fontSize = 20.sp, fontFamily = FontFamily(
                    Font(R.font.josefin_sans_bold)
                ), color = Color.White

            )
        }
    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxSize(), // Ensures it occupies available space
            reverseLayout = true
        ) {
            items(messageList.reversed()) { message ->
                MessageRow(messageModel = message)
            }
        }
    }
}

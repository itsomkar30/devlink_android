package com.devlink.app.connection_status

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.devlink.app.R
import com.devlink.app.authentication.SigninResponse
import com.devlink.app.ui.TopBar
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun ConnectionScreenView(navController: NavController, signinResponse: SigninResponse) {
    val connectionViewModel: ConnectionViewModel = viewModel()
    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = colorResource(R.color.jet_black),
            topBar = { TopBar(90, "Manage Connections", true, navController) }) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                ConnectionScreen(connectionViewModel, signinResponse = signinResponse)
            }
        }
    }
}

@Composable
fun ConnectionScreen(viewModel: ConnectionViewModel, signinResponse: SigninResponse) {
    val connections by remember {
        derivedStateOf {
            viewModel.connections
        }
    }
    LaunchedEffect(Unit) {
        if (connections.isEmpty()) {
            viewModel.getConnectionRequests(signinResponse)
        }
        viewModel.updateConnectionStatus("", "", "")
    }
//    val connections = viewModel.connections
    Log.i("Connections", connections.toString())


    if (connections.isEmpty()) {
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
                text = "Loading Connections",
                fontFamily = FontFamily(Font(R.font.josefin_sans_bold)),
                color = Color.White
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(connections) { connection ->
                ConnectionItem(connection)
            }
        }
    }

}


@Composable
fun ConnectionItem(connection: ConnectionRequestData) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .background(color = colorResource(R.color.black_modified))
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center
    )
    {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "From User: ${connection.fromUserId._id}",
                    fontFamily = FontFamily(Font(R.font.josefin_sans_bold)),
                    fontSize = 16.sp
                )
                Text(
                    text = "Status: ${
                        if (connection.status == "intrested") {
                            "Interested"
                        } else {
                            "Ignored"
                        }
                    }",
                    fontFamily = FontFamily(Font(R.font.josefin_sans_bold)),
                    fontSize = 14.sp
                )

                fun formatDateTime(utcDate: String): String {
                    val instant = Instant.parse(utcDate)
                    val formatter =
                        DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a") // Format: 21 Mar 2025, 01:13 PM
                    return instant.atZone(ZoneId.systemDefault()).format(formatter)
                }

                val formattedDate = formatDateTime(connection.createdAt)

                Text(

                    text = "Created at: $formattedDate",
                    fontFamily = FontFamily(Font(R.font.josefin_sans)),
                    fontSize = 13.sp,
                    textAlign = TextAlign.End

                )
            }
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .size(35.dp)
                        .clip(CircleShape)
                        .border(0.5.dp, color = Color.Green, shape = CircleShape)

                ) {
                    Icon(
                        imageVector = Icons.Outlined.Check,
                        contentDescription = "Interested icon",
                        tint = Color.Green
                    )
                }

            }
        }
    }
}
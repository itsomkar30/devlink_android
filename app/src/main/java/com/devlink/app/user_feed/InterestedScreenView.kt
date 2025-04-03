package com.devlink.app.user_feed

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.devlink.app.R
import com.devlink.app.authentication.LoginView
import com.devlink.app.connection_status.ConnectionViewModel
import com.devlink.app.ui.TopBar

@Composable
fun InterestedScreenView(navController: NavController, viewModel: InterestedIgnoredViewModel) {
    val interestedList = viewModel.interestedList
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBar(75, "Interested Request Sent", true, navController) },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(R.color.jet_black))
                .padding(paddingValues) // Ensures proper padding
        ) {
            Log.i("Interested List", interestedList.toString())
            LazyColumn(
                modifier = Modifier.height(screenHeight * 0.7f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(interestedList) { user ->
                    InterestedItem( user)
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(16.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Row(horizontalArrangement = Arrangement.Center) {
                    Icon(imageVector = Icons.Default.Info, contentDescription = "info")

                    Text(
                        text = "You cannot undo requests. These requests are limited to this session only",
                        fontFamily = FontFamily(Font(R.font.josefin_sans)),
                        fontSize = 16.sp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun InterestedItem( user: User) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .background(color = colorResource(R.color.black_modified))
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
package com.devlink.app

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.devlink.app.authentication.LoginView
import com.devlink.app.authentication.SigninResponse
import com.devlink.app.authentication.UserModel
import com.devlink.app.connection_status.ConnectionScreenView
import com.devlink.app.ui.theme.DevlinkTheme
import com.devlink.app.user_feed.FeedModel


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val viewModel = ViewModelProvider(this)[FeedModel::class.java]

            DevlinkTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(Modifier.padding(innerPadding)) {
                        Navigation()
//                        ConnectionScreenView(
//                            navController,
//                            SigninResponse(
//                                UserModel("",""),
//                                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2N2Q4NmE2MmY1MDNiM2IzNGU1ZWEzNGEiLCJpYXQiOjE3NDI1NjI2MzV9.x1irF-1jrU6CqNnp3AHBX8QAyH6e3PG_uuUFzc2RhBU"
//                            )
//                        )
//                        HomeScreenView(navController =navController)
//                        ChatPageView()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DevlinkTheme {
        LoginView()
    }
}
package com.devlink.app

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.devlink.app.authentication.LoginView
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
                Scaffold(modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Log.i("Padding", innerPadding.toString())
                    Surface(modifier = Modifier.padding(0.dp)) {
                        Navigation()
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
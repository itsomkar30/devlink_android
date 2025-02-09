package com.devlink.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devlink.app.ai_chat.ChatPageView
import com.devlink.app.authentication.LoginView
import com.devlink.app.authentication.SignupView
import com.devlink.app.ui.HomeScreenView

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.home_screen) {
        composable(Screen.home_screen) {
            HomeScreenView(navController)
        }
        composable(Screen.login_screen) {
            LoginView(navController)
        }
        composable(Screen.signup_screen) {
            SignupView(navController)
        }
        composable(Screen.gemini_screen) {
            ChatPageView(navController = navController)
        }
    }
}

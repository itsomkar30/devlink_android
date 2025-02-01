package com.devlink.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devlink.app.authentication.LoginView
import com.devlink.app.authentication.SignupView
import com.devlink.app.ui.HomeScreenView

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.login_screen) {
        composable(Screen.home_screen) {
            HomeScreenView(navController)
        }
        composable(Screen.login_screen) {
            LoginView(navController)
        }
        composable(Screen.signup_screen) {
            SignupView(navController)
        }
    }
}

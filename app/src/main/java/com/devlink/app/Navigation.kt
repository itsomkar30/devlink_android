package com.devlink.app

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devlink.app.ai_chat.AIChatPageView
import com.devlink.app.authentication.AutoLoginScreen
import com.devlink.app.authentication.LoginView
import com.devlink.app.authentication.LogoutViewModel
import com.devlink.app.authentication.SigninResponse
import com.devlink.app.authentication.SignupView
import com.devlink.app.authentication.UserModel
import com.devlink.app.authentication.UserPreferences
import com.devlink.app.chat.ChatScreenView
import com.devlink.app.connection_status.ConnectionScreenView
import com.devlink.app.connection_status.ConnectionViewModel
import com.devlink.app.create_post.CreatePostView
import com.devlink.app.profile.AboutScreenView
import com.devlink.app.profile.ProfileView
import com.devlink.app.ui.HomeScreenView
import com.devlink.app.user_feed.FeedModel
import com.devlink.app.user_feed.IgnoredScreenView
import com.devlink.app.user_feed.InterestedIgnoredViewModel
import com.devlink.app.user_feed.InterestedScreenView
import com.devlink.app.user_feed.SearchScreenView
import com.devlink.app.user_feed.UserData
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun Navigation() {
    val navController = rememberNavController()
    var isCheckingLogin by remember { mutableStateOf(true) }
    val context = LocalContext.current
    val interestedIgnoredViewModel: InterestedIgnoredViewModel = viewModel()


    LaunchedEffect(Unit) {
        Log.i("AutoSignIn", "Checking user data...")
//        isAutoLogin = true
        val (token, userId, email) = UserPreferences.getUserData(context)
        Log.i("AutoSignIn", "Token: $token, UserId: $userId, Email: $email")

        if (token != null && email != null) {
            try {
                Log.i("AutoSignIn", "Valid credentials found. Navigating to Home...")
                delay(1250)

                navController.navigate(Screen.home_screen + "/$userId/$email/$token") {
                    popUpTo(Screen.login_screen) { inclusive = true }
                }

                Log.i(
                    "AutoSignIn",
                    "Navigation executed. Current route after: ${navController.currentBackStackEntry?.destination?.route}"
                )

            } catch (e: Exception) {
                Log.e("AutoSignIn", "Navigation error: ${e.localizedMessage}", e)
            }

        } else {
            isCheckingLogin = false

        }


    }



    NavHost(navController = navController, startDestination = Screen.auto_login_screen) {
        composable(
            route = Screen.home_screen + "/{user_id}/{user_email}/{token}"
        ) { backStackEntry ->
            val user_id = backStackEntry.arguments?.getString("user_id") ?: "invalid user id"
            val user_email =
                backStackEntry.arguments?.getString("user_email") ?: "invalid user email"
            val token = backStackEntry.arguments?.getString("token") ?: "invalid user token"

            HomeScreenView(
                userModel = UserModel(id = user_id, email = user_email),
                navController = navController,
                feedModel = FeedModel(),
                signinResponse = SigninResponse(
                    user = UserModel(id = user_id, email = user_email),
                    token = token
                ),
                connectionViewModel = ConnectionViewModel(),
                interestedIgnoredViewModel = interestedIgnoredViewModel
            )
        }


        composable(Screen.login_screen) {
            LoginView(navController)
        }


        composable(Screen.signup_screen) {
            SignupView(navController)
        }


        composable(Screen.gemini_screen) {
            AIChatPageView(navController)
        }

        composable(Screen.create_post_screen) {
            CreatePostView(navController)
        }

        composable(Screen.about_screen) {
            AboutScreenView(navController)
        }

        composable(Screen.interested_screen) {
            InterestedScreenView(navController = navController, interestedIgnoredViewModel)
        }

        composable(Screen.ignored_screen) {
            IgnoredScreenView(navController = navController, interestedIgnoredViewModel)
        }

        composable(Screen.chat_screen + "/{token}") { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            ChatScreenView(token = token)
        }

        composable(Screen.search_screen + "/{token}") { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            SearchScreenView(navController = navController, token = token)
        }

        composable(Screen.auto_login_screen) {
            if (isCheckingLogin) {
                AutoLoginScreen(navController = navController)
            } else {
                LoginView(navController)
            }
        }

        composable(Screen.profile_screen + "/{token}") { backStackEntry ->
            val userData =
                navController.previousBackStackEntry?.savedStateHandle?.get<UserData>("userData")
                    ?: UserData("", "", "", "", "")
            val token = backStackEntry.arguments?.getString("token") ?: ""
            ProfileView(
                navController = navController,
                userData = userData,
                connectionViewModel = ConnectionViewModel(),
                token = token,
                viewModel = LogoutViewModel()
            )
        }

        composable(Screen.navigation_screen + "/{user_id}/{user_email}/{token}") { backStackEntry ->
            val user_id = backStackEntry.arguments?.getString("user_id") ?: "invalid user id"
            val user_email =
                backStackEntry.arguments?.getString("user_email") ?: "invalid user email"
            val token = backStackEntry.arguments?.getString("token") ?: "invalid user token"

            ConnectionScreenView(
                navController = navController, signinResponse = SigninResponse(
                    user = UserModel(id = user_id, email = user_email),
                    token = token
                )
            )

        }

    }

}

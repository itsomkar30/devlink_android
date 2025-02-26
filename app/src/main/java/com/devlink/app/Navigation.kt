package com.devlink.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devlink.app.ai_chat.ChatPageView
import com.devlink.app.authentication.LoginView
import com.devlink.app.authentication.SigninResponse
import com.devlink.app.authentication.SignupView
import com.devlink.app.authentication.UserModel
import com.devlink.app.create_post.CreatePostView
import com.devlink.app.ui.HomeScreenView
import com.devlink.app.user_feed.FeedModel

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.login_screen) {
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
                )
//                feed = FeedResponse()
            )
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

        composable(Screen.create_post_screen) {
            CreatePostView(navController)
        }


    }
}

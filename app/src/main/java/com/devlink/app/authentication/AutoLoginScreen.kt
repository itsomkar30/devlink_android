package com.devlink.app.authentication

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Surface
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.devlink.app.R
import com.devlink.app.Screen

@Composable
fun AutoLoginScreen(navController: NavController) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.jet_black))
    ) {
        val context = LocalContext.current
//        LaunchedEffect(Unit) {
//            autoSignIn(context= context, navController= navController)
//        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(R.color.jet_black)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            AsyncImage(
                model = R.raw.devlink_logo,
                contentDescription = "Devlink Logo"
            )
            Spacer(modifier = Modifier.height(32.dp))
            CircularProgressIndicator(
                color = Color.White,
                strokeWidth = 4.dp
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Auto Signing-in",
                fontFamily = FontFamily(Font(R.font.josefin_sans_bold)),
                color = Color.White
            )

        }
    }
}




suspend fun autoSignIn(
    context: Context,
    navController: NavController
) {
    val (token, userId, email) = UserPreferences.getUserData(context)
    Log.i("AutoSignIn Screen", "Token: $token, UserId: $userId, Email: $email")

    if (token != null && email != null) {
        Log.i("Response", "/$userId/$email/$token")
        // Navigate directly to home screen
        navController.navigate(Screen.home_screen + "/$userId/$email/$token") {
            popUpTo(Screen.login_screen) { inclusive = true }
        }
    }
}
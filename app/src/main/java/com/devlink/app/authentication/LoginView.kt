package com.devlink.app.authentication

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.devlink.app.R
import com.devlink.app.Screen
import com.devlink.app.ui.AppFonts


@Composable
fun LoginView(navController: NavController= rememberNavController()) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val cardHeight = screenHeight * 0.70f

    Box(modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(-1f)
                .offset(y = (-70).dp),
            contentAlignment = Alignment.TopCenter

        ) {
            LottieAnimationLoop(R.raw.login_gradient, 500.dp)
        }
        // Background card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(cardHeight)
                .align(Alignment.BottomStart)
                .offset(y = 50.dp),
            shape = RoundedCornerShape(
                topStart = 64.dp,
                topEnd = 64.dp,
                bottomStart = 0.dp,
                bottomEnd = 0.dp
            ),
            elevation = CardDefaults.cardElevation(16.dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(R.color.black_modified))
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = cardHeight * 0.25f, start = 16.dp, end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var email by rememberSaveable { mutableStateOf("") }
                var password by rememberSaveable { mutableStateOf("") }

                Text(
                    text = "Start connecting with Devlink",
                    fontFamily = AppFonts.josefin_bold,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(screenHeight * 0.03f))

                ModifiedTextField(
                    name = email, onNameChange = {
                        email = it
                    },
                    label = "Email",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(screenHeight * 0.06f)
                )
                Spacer(modifier = Modifier.height(screenHeight * 0.03f))
                ModifiedTextField(
                    name = password, onNameChange = {
                        password = it
                    },
                    label = "Password",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(screenHeight * 0.06f)
                )

                Spacer(modifier = Modifier.height(screenHeight * 0.03f))

                ModifiedButton(
                    text = "Login",
                    onButtonClick = {
                        println("Button clicked!")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(screenHeight * 0.06f)
                )

                Spacer(modifier = Modifier.height(screenHeight * 0.04f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Don't have an account?  ",
                        fontFamily = AppFonts.josefin_bold,
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = "Create account here",
                        fontFamily = AppFonts.josefin_bold,
                        style = MaterialTheme.typography.titleSmall,
                        color = colorResource(R.color.blue),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.clickable {
                            navController.navigate(Screen.signup_screen)
                        }
                    )
                }

            }

        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = screenHeight * -0.18f)
                .zIndex(2f), // Ensures it's above all other elements
            contentAlignment = Alignment.Center
        ) {
            LottieAnimationLoop(R.raw.login_animation, screenWidth * 1.0f)
        }
    }
}


@Composable
fun LottieAnimationLoop(fileName: Int = R.raw.login_animation, size: Dp = 350.dp) {

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(fileName))


    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    LottieAnimation(
        composition = composition,
        progress = progress,
        modifier = Modifier
            .size(size)
    )
}


@Composable
fun ModifiedTextField(
    name: String,
    onNameChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = name,
        onValueChange = {
            onNameChange(it)
        },
        label = { Text(label) },

        colors = OutlinedTextFieldDefaults.colors(

            focusedBorderColor = colorResource(R.color.white),
            unfocusedTextColor = colorResource(R.color.white),
            cursorColor = colorResource(R.color.white),
            focusedLabelColor = colorResource(R.color.white),
            unfocusedLabelColor = colorResource(R.color.white),


            ), shape = RectangleShape,
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp),
        textStyle = TextStyle(
            fontFamily = AppFonts.josefin_bold,
            color = Color.White,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize

        )

    )
}


@Preview(showBackground = true)
@Composable
fun TestPreview() {
//    CustomCard()
}
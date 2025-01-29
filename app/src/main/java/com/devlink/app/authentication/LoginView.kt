package com.devlink.app.authentication

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.devlink.app.R
import com.devlink.app.ui.AppFonts

@Composable
fun LoginView() {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val cardHeight = screenHeight * 0.75f

    Box(modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(-1f)
                .offset(y = (-70).dp),
            contentAlignment = Alignment.TopCenter

        ) {
            LottieAnimationLoop(R.raw.login_gradient, 450)
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
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(R.color.black_modified))
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = cardHeight*0.25f, start = 12.dp, end = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var email by rememberSaveable { mutableStateOf("") }
                var password by rememberSaveable { mutableStateOf("") }

                Text(
                    text = "Start connecting with Devlink",
                    fontFamily = AppFonts.josefin_bold,
                    fontSize =20.sp,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(screenHeight*0.05f))

                ModifiedTextField(
                    name = email, onNameChange = {
                        email = it
                    },
                    label = "Email"
                )
                Spacer(modifier = Modifier.height(10.dp))
                ModifiedTextField(
                    name = password, onNameChange = {
                        password = it
                    },
                    label = "Password"
                )
            }

        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = screenHeight * -0.20f)
                .zIndex(2f), // Ensures it's above all other elements
            contentAlignment = Alignment.Center
        ) {
            LottieAnimationLoop(R.raw.login_animation, 375)
        }
    }
}


@Composable
fun LottieAnimationLoop(fileName: Int = R.raw.login_animation, size: Int = 350) {

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(fileName))


    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    LottieAnimation(
        composition = composition,
        progress = progress,
        modifier = Modifier
            .size(size.dp)
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


            ), shape = RectangleShape, // Soft rounded edges
        modifier = modifier
            .fillMaxWidth(),
        textStyle = TextStyle(
            fontFamily = AppFonts.josefin,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

    )
}


@Preview(showBackground = true)
@Composable
fun TestPreview() {
//    CustomCard()
}
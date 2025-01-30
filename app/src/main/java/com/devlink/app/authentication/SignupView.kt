package com.devlink.app.authentication

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.devlink.app.R
import com.devlink.app.ui.AppFonts

@Composable
fun SignupView(modifier: Modifier = Modifier) {

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val cardHeight = screenHeight * 0.85f


    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(-1f)
                .offset(y = -screenHeight * 0.2f),
            contentAlignment = Alignment.TopCenter

        ) {
            LottieAnimationLoop(R.raw.login_gradient, 500.dp)
        }

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
                    .padding(top = cardHeight * 0.10f, start = 16.dp, end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Create your account",
                        fontFamily = AppFonts.josefin_bold,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Start,
                        letterSpacing = 2.sp
                    )
                }
                Spacer(modifier = Modifier.height(screenHeight * 0.02f))

                var email by rememberSaveable { mutableStateOf("") }
                var password by rememberSaveable { mutableStateOf("") }
                var city by rememberSaveable { mutableStateOf("") }
                var age by rememberSaveable { mutableStateOf("") }

                ModifiedTextField(
                    name = email,
                    onNameChange = {
                        email = it
                    },
                    label = "Email"
                )
                Spacer(modifier = Modifier.height(screenHeight * 0.02f))
                ModifiedTextField(
                    name = password,
                    onNameChange = {
                        password = it
                    },
                    label = "Password"
                )

                Spacer(modifier = Modifier.height(screenHeight * 0.02f))


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    ModifiedTextField(
                        name = city,
                        onNameChange = {
                            city = it
                        },
                        label = "City",
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(screenWidth * 0.02f))

                    ModifiedTextField(
                        name = age,
                        onNameChange = {
                            age = it
                        },
                        label = "Age",
                        modifier = Modifier.weight(1f)
                    )
                }


                Spacer(modifier = Modifier.height(screenHeight * 0.02f))


                ModifiedButton(
                    text = "Create Account",
                    onButtonClick = {
                        println("Button clicked!")
                    },
                    modifier = Modifier
                        .fillMaxWidth().height(screenHeight*0.06f)
                )


            }

        }


    }

}


@Composable
fun ModifiedButton(text: String, onButtonClick: () -> Unit, modifier: Modifier = Modifier) {

    Button(
        onClick = onButtonClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        shape = RectangleShape,
        modifier = modifier
    ) {

        Text(
            text = text,
            fontFamily = AppFonts.josefin_bold,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize
        )
    }
}
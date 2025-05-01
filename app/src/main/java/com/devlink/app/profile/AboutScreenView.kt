package com.devlink.app.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.devlink.app.R
import com.devlink.app.ui.TopBar

@Composable
fun AboutScreenView(navController: NavController) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        backgroundColor = colorResource(R.color.jet_black),
        topBar = {
            TopBar(
                title = "About and Source Code",
                showBackButton = true,
                navController = navController
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text(
                text = "Developed by",
                fontFamily = (FontFamily(Font(R.font.josefin_sans_bold))),
                fontSize = 20.sp,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.padding(start = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(175.dp)
                    .padding(16.dp)
            ) {

                val uriHandler = LocalUriHandler.current
                val linkedInOmkar = "https://www.linkedin.com/in/omkar-prabhudesai/"
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column() {
                        AsyncImage(
                            model = R.raw.omkar,
                            contentDescription = "Profile picture",
                            modifier = Modifier
                                .size(75.dp)
                                .clip(CircleShape)
                        )
                    }
                    Column() {
                        Text(
                            text = "Omkar Prabhudesai",
                            fontFamily = (FontFamily(Font(R.font.josefin_sans_bold))),
                            fontSize = 20.sp,
                            modifier = Modifier.padding(start = 8.dp)
                        )

                        Text(
                            text = "Frontend Android App Development",
                            fontFamily = (FontFamily(Font(R.font.josefin_sans_bold))),
                            fontSize = 16.sp,
                            modifier = Modifier.padding(start = 8.dp)
                        )

                        Text(
                            text = "Languages: Kotlin, Jetpack Compose",
                            fontFamily = (FontFamily(Font(R.font.josefin_sans_bold))),
                            fontSize = 14.sp,
                            modifier = Modifier.padding(start = 8.dp)
                        )

                        Divider(
                            color = Color.White,
                            thickness = 1.dp,
                            modifier = Modifier.padding(start = 8.dp, top = 12.dp, bottom = 12.dp)
                        )



                        Text(
                            text = "View LinkedIn",
                            fontFamily = (FontFamily(Font(R.font.josefin_sans_bold))),
                            fontSize = 16.sp,
                            textDecoration = TextDecoration.Underline,
                            color = Color(0xFF0a66c2),
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .clickable { uriHandler.openUri(linkedInOmkar) }
                        )
                    }
                }

            }



            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(175.dp)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                val uriHandler = LocalUriHandler.current
                val linkedInPrajjval = "https://www.linkedin.com/in/prajjval-jaiswal/"

                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column() {
                        AsyncImage(
                            model = R.raw.prajjval,
                            contentDescription = "Profile picture",
                            modifier = Modifier
                                .size(75.dp)
                                .clip(CircleShape)
                        )
                    }
                    Column() {
                        Text(
                            text = "Prajjval Jaiswal",
                            fontFamily = (FontFamily(Font(R.font.josefin_sans_bold))),
                            fontSize = 20.sp,
                            modifier = Modifier.padding(start = 8.dp)
                        )

                        Text(
                            text = "Backend and API Development",
                            fontFamily = (FontFamily(Font(R.font.josefin_sans_bold))),
                            fontSize = 16.sp,
                            modifier = Modifier.padding(start = 8.dp)
                        )

                        Text(
                            text = "Languages: MongoDB, ExpressJS, NodeJS",
                            fontFamily = (FontFamily(Font(R.font.josefin_sans_bold))),
                            fontSize = 14.sp,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                        Divider(
                            color = Color.White,
                            thickness = 1.dp,
                            modifier = Modifier.padding(start = 8.dp, top = 12.dp, bottom = 12.dp)
                        )


                        Text(
                            text = "View LinkedIn",
                            fontFamily = (FontFamily(Font(R.font.josefin_sans_bold))),
                            fontSize = 16.sp,
                            textDecoration = TextDecoration.Underline,
                            color = Color(0xFF0a66c2),
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .clickable { uriHandler.openUri(linkedInPrajjval) }
                        )
                    }

                }

            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Source Code",
                fontFamily = (FontFamily(Font(R.font.josefin_sans_bold))),
                fontSize = 20.sp,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.padding(start = 16.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    val uriHandler = LocalUriHandler.current
                    val githubFrontend = "https://github.com/itsomkar30/devlink_android"


                    Text(
                        text = "Android app (Frontend)",
                        fontFamily = (FontFamily(Font(R.font.josefin_sans_bold))),
                        fontSize = 16.sp,
                        modifier = Modifier
                    )

                    Text(
                        text = "GitHub",
                        fontFamily = (FontFamily(Font(R.font.josefin_sans_bold))),
                        color = Color(0xFF0a66c2),
                        textDecoration = TextDecoration.Underline,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .clickable { uriHandler.openUri(githubFrontend) }
                    )
                }

                Divider(
                    color = Color.White,
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    val uriHandler = LocalUriHandler.current
                    val githubBackend = "https://github.com/prajjvaljaiswal/temp_deploye"

                    Text(
                        text = "Backend API",
                        fontFamily = (FontFamily(Font(R.font.josefin_sans_bold))),
                        fontSize = 16.sp,
                        modifier = Modifier

                    )

                    Text(
                        text = "GitHub",
                        fontFamily = (FontFamily(Font(R.font.josefin_sans_bold))),
                        color = Color(0xFF0a66c2),
                        textDecoration = TextDecoration.Underline,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .clickable { uriHandler.openUri(githubBackend) }
                    )
                }

            }

        }
    }
}
package com.devlink.app.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.devlink.app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(size: Int, title: String, showBackButton: Boolean, navController: NavController) {
    TopAppBar(
        modifier = Modifier.height(size.dp),
        navigationIcon = {
            if (showBackButton) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back Button",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(top = 30.dp, start = 20.dp)
                        .size(25.dp)
                        .clickable {
                            navController.navigateUp()
                        }

                )

            } else {
                null
            }
        },
        title = {
            Text(
                title,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.josefin_sans_bold)),
                modifier = Modifier.padding(top = 30.dp, start = 30.dp)
            )
        },
        colors = TopAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = Color.White,
            scrolledContainerColor = Color.White,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White
        )


    )
}
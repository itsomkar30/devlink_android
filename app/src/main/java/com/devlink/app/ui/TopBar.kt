package com.devlink.app.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(modifier: Modifier = Modifier) {
    TopAppBar(
        title = { Text("") },
        colors = TopAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = Color.White,
            scrolledContainerColor = Color.White,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White
        )


    )
}
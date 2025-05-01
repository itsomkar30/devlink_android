package com.devlink.app

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.devlink.app.user_feed.User

@Composable
fun BottomBtnBar(
    modifier: Modifier = Modifier,
    navController: NavController,

) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp


    Column(
        modifier = Modifier
            .height(50.dp)
            .fillMaxSize()
            .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
            .background(colorResource(R.color.jet_black))
            .clickable { },
        verticalArrangement = Arrangement.Bottom
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .clickable { navController.navigate(Screen.ignored_screen)},
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Ignored",
                    fontFamily = FontFamily(Font(R.font.josefin_sans_bold)),
                    color = Color.White
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .clickable { navController.navigate(Screen.interested_screen) },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Interested",
                    fontFamily = FontFamily(Font(R.font.josefin_sans_bold)),
                    color = Color.White
                )
            }
        }

    }
}
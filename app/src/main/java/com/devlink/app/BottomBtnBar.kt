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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp

@Composable
fun BottomBtnBar(modifier: Modifier = Modifier) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp


    Column(
        modifier = Modifier
            .height(55.dp)
            .fillMaxSize()
            .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
            .background(colorResource(R.color.jet_black))
            .clickable {  },
        verticalArrangement = Arrangement.Bottom
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .clickable {  },
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
                    .clickable {  },
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


//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(screenHeight * 0.055f)
//            .fillMaxHeight()
//            .padding(start = screenWidth * 0.02f)
//    ) {
//        Row() {
//            Column(
//                modifier = Modifier
//                    .width(screenWidth * 0.6f)
//                    .height(screenHeight * 0.055f)
//                    .border(
//                        1.dp,
//                        Color.White, RectangleShape
//                    )
//                    .padding(
//                        0.dp
//                    )
//            ) {
//                Row(modifier = Modifier.fillMaxSize()) {
//                    Column(
//                        modifier = Modifier
//                            .weight(1f)
//                            .fillMaxHeight()
//                            .border(1.dp, Color.White, RectangleShape)
//                            .clickable { },
//                        verticalArrangement = Arrangement.Center,
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        Text(
//                            "Pending",
//                            fontFamily = FontFamily(Font(R.font.josefin_sans_bold)),
//                            color = Color.White
//                        )
//                    }
//                    Column(
//                        modifier = Modifier
//                            .weight(1f)
//                            .fillMaxHeight()
//                            .border(1.dp, Color.White, RectangleShape)
//                            .clickable { }
//                            .background(Color.White),
//                        verticalArrangement = Arrangement.Center,
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        Text("Accepted", fontFamily = FontFamily(Font(R.font.josefin_sans_bold)), color = Color.Black)
//                    }
//
//                }
//            }
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .fillMaxHeight()
//                    .padding(end = screenWidth * 0.02f),
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.End
//            ) {
//                Icon(
//                    imageVector = Icons.Outlined.AccountCircle,
//                    contentDescription = "Profile",
//                    tint = Color.White,
//                    modifier = Modifier.size(screenHeight * 0.05f)
//                )
//            }
//        }
//
//
//    }
//    Spacer(modifier = Modifier.padding(screenHeight * 0.01f))

}
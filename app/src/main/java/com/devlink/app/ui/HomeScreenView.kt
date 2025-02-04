package com.devlink.app.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.devlink.app.BottomBtnBar
import com.devlink.app.BottomNavBar
import com.devlink.app.R

//
//@Composable
//fun HomeScreenView(
//    navController: NavController = rememberNavController(),
//    modifier: Modifier = Modifier
//) {
//
//    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
//    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
//
//    Box(modifier = Modifier.fillMaxSize()) {
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(color = colorResource(R.color.black_modified))
//        ) {
//            Column(
//                modifier = Modifier.fillMaxSize(),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center
//            ) {
//                DevListItem()
//            }
//        }
//    }
//
//}


@Composable
fun HomeScreenView(
    navController: NavController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.black_modified)), // Black background
            containerColor = colorResource(R.color.black_modified), // Ensure Scaffold background is black
            topBar = { TopBar(modifier = Modifier) },
            bottomBar = { BottomNavBar(modifier = Modifier.zIndex(3f)) },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {},
                    modifier = Modifier
//                        .padding(
//                            end = screenWidth * 0.06f,
//                            bottom = screenHeight * 0.02f
//                        )
                        .size(screenWidth * 0.14f),
                    shape = CircleShape,
                    backgroundColor = colorResource(R.color.snap_yellow),
                    contentColor = colorResource(R.color.black)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = "Search",
                        tint = Color.Black
                    )

                }
            },
            floatingActionButtonPosition = FabPosition.Center,
            content = { paddingValues -> // Handles insets like status bar, nav bar
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues) // Apply padding to avoid overlap with system UI
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        DevListItem()


                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Bottom
                        ) {
                            BottomBtnBar()
                        }
                    }
                }
            }
        )
    }
}


@Composable
fun DevListItem() {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    Column(
        modifier = Modifier
            .padding(0.dp)
            .height(screenHeight * 0.70f)
            .width(screenWidth * 0.96f)
            .fillMaxSize()
            .shadow(
                elevation = screenWidth * 1f, // Elevation for the shadow
                shape = RoundedCornerShape(screenWidth * 0.15f),
                ambientColor = Color.White, // Ambient shadow color
                spotColor = Color.White.copy(alpha = 1f) // Spot shadow color
            )
            .clip(RoundedCornerShape(screenWidth * 0.15f))
            .background(color = colorResource(R.color.white).copy(alpha = 0.4f))
            .blur(radius = 100.dp)
            .border(
                BorderStroke(1.dp, Color.White),
                shape = RoundedCornerShape(screenWidth * 0.15f)
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


    }
}

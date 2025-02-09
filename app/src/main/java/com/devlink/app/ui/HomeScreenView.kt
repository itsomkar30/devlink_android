package com.devlink.app.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.devlink.app.BottomBtnBar
import com.devlink.app.BottomNavBar
import com.devlink.app.R
import com.devlink.app.data.DummyData
import com.devlink.app.data.dummyDataDelete
import com.devlink.app.data.dummyDataList

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


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreenView(
    navController: NavController,
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
            topBar = {
                TopBar(
                    size = 50,
                    title = "",
                    showBackButton = false,
                    navController = navController
                )
            },
            bottomBar = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    BottomBtnBar(
                        modifier = Modifier.zIndex(
                            3f
                        )
                    )
                    BottomNavBar(
                        modifier = Modifier.zIndex(
                            3f
                        ), navController
                    )
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                    },
                    modifier = Modifier
//                        .padding(
//                            end = screenWidth * 0.06f,
//                            bottom = screenHeight * 0.02f
//                        )
                        .offset(y = 50.dp)
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
//                        DevListItem()

//
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            dummyDataList.reversed().forEach { item ->
                                DevListItem(
                                    item = item,
                                    onSwiped = { dummyDataDelete(item) }
                                )
                            }
                        }


//
//                        Column(
//                            modifier = Modifier
//                                .fillMaxSize(),
//                            horizontalAlignment = Alignment.CenterHorizontally,
//                            verticalArrangement = Arrangement.Bottom
//                        ) {
//                            BottomBtnBar()
//                        }
                    }
                }
            }
        )
    }
}

@Composable
fun DevListItem(item: DummyData, onSwiped: () -> Unit) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    var offsetX by remember { mutableFloatStateOf(0f) }
    var rotation by remember { mutableFloatStateOf(0f) }

    val animatedOffsetX by animateFloatAsState(targetValue = offsetX)
    val animatedRotation by animateFloatAsState(targetValue = rotation)

    Box(
        modifier = Modifier
            .padding(0.dp)
            .height(screenHeight * 0.70f)
            .width(screenWidth * 0.94f)
            .graphicsLayer {
                translationX = animatedOffsetX
                rotationZ = animatedRotation
            }
            .shadow(
                elevation = screenWidth * 0.05f, // Adjusted elevation for realistic shadow
                shape = RoundedCornerShape(screenWidth * 0.15f),
                ambientColor = Color.Gray.copy(alpha = 0.5f), // Softer shadow color
                spotColor = Color.Black.copy(alpha = 0.3f)
            )
            .clip(RoundedCornerShape(screenWidth * 0.15f))
            .background(color = colorResource(R.color.white).copy(alpha = 0.9f))
            .border(
                BorderStroke(1.dp, Color.White),
                shape = RoundedCornerShape(screenWidth * 0.15f)
            )
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        if (offsetX > 400 || offsetX < -400) {
                            onSwiped()  // Remove card after swipe
                        } else {
                            offsetX = 0f
                            rotation = 0f
                        }
                    },
                    onDrag = { change, dragAmount ->
                        offsetX += dragAmount.x
                        rotation = (offsetX / 20).coerceIn(-20f, 20f)
                        change.consume()
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.skill,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray
            )
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeBackground(dismissState: DismissState) {
    val direction = dismissState.dismissDirection ?: return  // Return if no swipe is happening

    val color = when (dismissState.targetValue) {
        DismissValue.Default -> Color.Transparent
        DismissValue.DismissedToEnd -> Color(0xFF4CAF50)  // Green for right swipe
        DismissValue.DismissedToStart -> Color(0xFFF44336)  // Red for left swipe
    }

    val alignment = when (direction) {
        DismissDirection.StartToEnd -> Alignment.CenterStart  // Align to start when swiping right
        DismissDirection.EndToStart -> Alignment.CenterEnd    // Align to end when swiping left
    }

    val icon = when (direction) {
        DismissDirection.StartToEnd -> Icons.Default.Check  // Icon for right swipe
        DismissDirection.EndToStart -> Icons.Default.Delete  // Icon for left swipe
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(horizontal = 20.dp),
        contentAlignment = alignment
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White
        )
    }
}

//
//@OptIn(ExperimentalMaterialApi::class)
//@Composable
//fun SwipeBackground(dismissState: DismissState) {
//    val direction = dismissState.dismissDirection ?: return  // Return if no swipe is happening
//
//    val color = when (dismissState.targetValue) {
//        DismissValue.Default -> Color.Transparent
//        DismissValue.DismissedToEnd -> Color(0xFF4CAF50)  // Green for right swipe
//        DismissValue.DismissedToStart -> Color(0xFFF44336)  // Red for left swipe
//    }
//
//    val alignment = when (direction) {
//        DismissDirection.StartToEnd -> Alignment.CenterStart  // Align to start when swiping right
//        DismissDirection.EndToStart -> Alignment.CenterEnd    // Align to end when swiping left
//    }
//
//    val icon = when (direction) {
//        DismissDirection.StartToEnd -> Icons.Default.Check  // Icon for right swipe
//        DismissDirection.EndToStart -> Icons.Default.Delete  // Icon for left swipe
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(color)
//            .padding(horizontal = 20.dp),
//        contentAlignment = alignment
//    ) {
//        Icon(
//            imageVector = icon,
//            contentDescription = null,
//            tint = Color.White
//        )
//    }
//}
//

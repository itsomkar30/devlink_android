package com.devlink.app.ui

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.devlink.app.BottomBtnBar
import com.devlink.app.BottomNavBar
import com.devlink.app.R
import com.devlink.app.Screen
import com.devlink.app.authentication.SigninResponse
import com.devlink.app.authentication.UserModel
import com.devlink.app.user_feed.FeedModel
import com.devlink.app.user_feed.UserData
import kotlinx.coroutines.launch

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
fun DrawerItem(title: String, icon: ImageVector, navController: NavController, route: String = "") {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(route)
            }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = title)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = title, fontFamily = FontFamily(Font(R.font.josefin_sans_bold)))
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreenView(
    userModel: UserModel,
    navController: NavController,
    feedModel: FeedModel = viewModel(),
    signinResponse: SigninResponse,
    modifier: Modifier = Modifier
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.fillMaxWidth(0.75f)
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    AsyncImage(
                        model = R.raw.nav_drawer_background,
                        contentDescription = "",
                        modifier = Modifier.fillMaxWidth()
                    )

                    AsyncImage(
                        model = R.raw.sample_user,
                        contentDescription = "",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .align(Alignment.Center)
                            .zIndex(1f)
                    )
                }

                DrawerItem(
                    title = "Create Post",
                    icon = Icons.Default.Add,
                    navController = navController,
                    route = Screen.create_post_screen
                )

                DrawerItem(
                    title = "Manage Account",
                    icon = Icons.Default.AccountCircle,
                    navController = navController,
                    route = Screen.home_screen
                )

                DrawerItem(
                    title = "About And Source Code",
                    icon = Icons.Default.Info,
                    navController = navController,
                    route = Screen.home_screen

                )


            }

        }
    ) {
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

                            TopAccountBar(drawerState = drawerState, userModel = userModel)

                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.TopCenter
                            ) {
                                val userList by remember { feedModel.feedDataResponse }

                                LaunchedEffect(Unit) {
                                    feedModel.FeedCheck(
                                        userModel = UserModel(
                                            userModel.id,
                                            userModel.email
                                        ),
                                        signinResponse = SigninResponse(
                                            user = UserModel(
                                                userModel.id,
                                                userModel.email
                                            ), token = signinResponse.token
                                        )
                                    )
                                }
//                                val userList = feedModel.feedDataResponse.value
                                Log.i("Response New Data JSON", userList.toString())

                                if (userList.isEmpty()) {
                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        CircularProgressIndicator(
                                            color = Color.White,
                                            strokeWidth = 4.dp
                                        )
                                        Spacer(modifier = Modifier.height(16.dp))
                                        Text(
                                            text = "Loading Feed",
                                            fontFamily = FontFamily(Font(R.font.josefin_sans_bold)),
                                            color = Color.White
                                        )
                                    }
                                } else {
                                    userList.reversed().forEach { user ->
                                        DevListItem(
                                            item = user,
                                            onSwiped = { },
                                            userModel = UserModel(userModel.id, userModel.email),
                                            feedModel = feedModel,
                                            signinResponse = SigninResponse(
                                                user = UserModel(
                                                    userModel.id,
                                                    userModel.email
                                                ), token = signinResponse.token
                                            )
                                        )
                                    }
                                }

                            }
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun TopAccountBar(drawerState: DrawerState, userModel: UserModel) {
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.Transparent),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
//            Text(
//                text = "Welcome ${userModel.email}",
//                color = Color.White,
//                fontFamily = FontFamily(Font(R.font.josefin_sans_bold)),
//                fontSize = 24.sp
//            )
            AsyncImage(
                model = R.raw.devlink_logo,
                contentDescription = "App logo",
                modifier = Modifier.height(30.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = R.raw.sample_user,
                    contentDescription = "Account",
                    modifier = Modifier
                        .size(35.dp)
                        .clip(CircleShape)
                        .clickable {

                            coroutineScope.launch {
                                drawerState.open()
                            }
                        }
                )
                Spacer(modifier = Modifier.width(20.dp))
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "Notification",
                    modifier = Modifier.size(35.dp),
                    tint = Color.White
                )
            }
        }
    }
}


@Composable
fun DevListItem(
    item: UserData,
    onSwiped: () -> Unit,
    userModel: UserModel,
    feedModel: FeedModel,
    signinResponse: SigninResponse
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    var offsetX by remember { mutableFloatStateOf(0f) }
    var rotation by remember { mutableFloatStateOf(0f) }

    val animatedOffsetX by animateFloatAsState(targetValue = offsetX)
    val animatedRotation by animateFloatAsState(targetValue = rotation)

    Box(
        modifier = Modifier
            .padding(0.dp)
            .height(screenHeight * 0.65f)
            .width(screenWidth * 0.94f)
            .graphicsLayer {
                translationX = animatedOffsetX
                rotationZ = animatedRotation
            }
            .shadow(
                elevation = screenWidth * 0.05f,
                shape = RoundedCornerShape(screenWidth * 0.15f),
                ambientColor = Color.Gray.copy(alpha = 0.5f),
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
                        when {
                            offsetX > 400 -> {
//                                onSwipedRight()  // Right Swipe Action
                                Log.i("Connect User id",item._id )
                                Log.i("Connect User status","intrested" )
                                Log.i("Connect User id",signinResponse.token.toString())

                                feedModel.sendConnectionRequest(
                                    toUserId = item._id,
                                    status = "intrested",
                                    token = signinResponse.token.toString()
                                )
                                offsetX = screenWidth.value
                            }

                            offsetX < -400 -> {
//                                onSwipedLeft()   // Left Swipe Action
                                feedModel.sendConnectionRequest(
                                    toUserId = item._id,
                                    status = "ignored",
                                    token = signinResponse.token.toString()
                                )
                                offsetX = -screenWidth.value
                            }

                            else -> {
                                offsetX = 0f
                                rotation = 0f
                            }
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
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
//                    .height(screenHeight * 0.45f)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(screenHeight * 0.45f)
//                        .background(Color.Yellow)
                ) {
                    AsyncImage(
                        model = if (item.photoURL != "" || item.photoURL.isNotEmpty()) {
                            item.photoURL
                        } else R.raw.default_user, contentDescription = "User Photo",
                        modifier = Modifier,
                        contentScale = ContentScale.Crop

                    )
                }



                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, bottom = 15.dp, start = 15.dp, end = 15.dp)
                        .clip(
                            RoundedCornerShape(
                                bottomStart = screenWidth * 0.15f,
                                bottomEnd = screenWidth * 0.15f
                            )
                        )
//                        .background(Color.Cyan)
                        .height(screenHeight * 0.18f)
                ) {
                    Text(
                        text = "${item.firstname} ${item.lastname}",
                        fontFamily = FontFamily(Font(R.font.josefin_sans_bold)),
                        fontSize = 45.sp,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Work mail: ",
                            fontFamily = FontFamily(Font(R.font.josefin_sans_bold)),
                            color = Color.Gray
                        )
                        Text(
                            text = item.email,
                            fontFamily = FontFamily(Font(R.font.josefin_sans_bold)),
                            color = Color.Gray
                        )
                    }
                }
            }


        }
    }
}
fun onSwipedRight(){

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

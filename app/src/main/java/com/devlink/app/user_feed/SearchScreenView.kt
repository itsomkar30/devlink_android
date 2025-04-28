package com.devlink.app.user_feed

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.devlink.app.R
import com.devlink.app.authentication.LottieAnimationLoop
import com.devlink.app.authentication.ModifiedTextField
import com.devlink.app.ui.TopBar

@Composable
fun SearchScreenView(navController: NavController, token: String) {
    val feedModel: FeedModel = viewModel()
    val userList = feedModel.skillFeedDataResponse
    var searchSkill by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBar(75, "Search Users", true, navController) },
        containerColor = colorResource(R.color.jet_black)
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = colorResource(R.color.jet_black)),
                verticalArrangement = Arrangement.Top,

                ) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    ModifiedTextField(
                        name = searchSkill,
                        onNameChange = { searchSkill = it },
                        label = "Search by Skill",
                        isSingleLine = false,
                        modifier = Modifier
                            .weight(1f) // Prevents shrinking
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    IconButton(
                        onClick = {
                            feedModel.fetchFeedFromSkill(token, searchSkill.trim())
                        },
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(colorResource(R.color.snap_yellow))
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = "Search",
                            tint = Color.Black
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                if (userList.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) { 
                        LottieAnimationLoop(
                            fileName = R.raw.search_screen_animation,
                            size = 300.dp
                        )
                    }
                } else {
                    userList.forEach { user ->
                        SearchUserItem(
                            user = user,
                            token = token,
                            feedModel = feedModel,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun SearchUserItem(user: User, token: String, feedModel: FeedModel, navController: NavController) {
    var showDialog by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(85.dp)
            .background(color = colorResource(R.color.black_modified))
            .clickable { showDialog = true }
            .padding(8.dp)

    ) {

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    model = if (user?.photoURL != "" || user?.photoURL!!.isNotEmpty()) {
                        user?.photoURL.toString()
                    } else {
                        R.raw.default_dp
                    },
                    contentDescription = "User Profile Picture",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "${user?.firstname} ${user?.lastname}",
                    fontFamily = FontFamily(Font(R.font.josefin_sans_bold)),
                    fontSize = 18.sp
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                ) {
                    Text(
                        text = "Skills: ",
                        fontFamily = FontFamily(Font(R.font.josefin_sans_bold)),
                        fontSize = 15.sp
                    )
                    LazyRow(verticalAlignment = Alignment.CenterVertically) {
                        items(user.skills) { skill ->
                            SkillItem(skill)
                        }
                    }
                }
            }
        }

    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {},
            title = {
                Text(
                    text = "Send the Connection Request",
                    fontFamily = (FontFamily(Font(R.font.josefin_sans_bold))),
                    fontSize = 22.sp
                )
            },
            text = {
                Column(
                    modifier = Modifier.height(250.dp),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.Start
                ) {

                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column() {
                            AsyncImage(
                                model = if (user?.photoURL != "" || user?.photoURL!!.isNotEmpty()) {
                                    user?.photoURL.toString()
                                } else {
                                    R.raw.default_dp
                                },
                                contentDescription = "User Profile Picture",
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(CircleShape)
                            )
                        }
                        Column(
                            modifier = Modifier.padding(start = 8.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "${user?.firstname} ${user?.lastname}",
                                fontFamily = FontFamily(Font(R.font.josefin_sans_bold)),
                                fontSize = 18.sp
                            )
                            Text(
                                text = "Uid: ${user?._id}",
                                fontFamily = (FontFamily(Font(R.font.josefin_sans))),
                                fontSize = 12.sp,
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        IconButton(
                            onClick = {
                                showDialog = false
                            },
                            modifier = Modifier
                                .size(35.dp)
                                .clip(CircleShape)
                                .border(
                                    0.5.dp,
                                    color = Color.Red,
                                    shape = CircleShape
                                )

                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Close,
                                contentDescription = "Rejected button",
                                tint = Color.Red
                            )
                        }

                        IconButton(
                            onClick = {
                                feedModel.sendConnectionRequest(
                                    toUserId = user?._id.toString(),
                                    status = "intrested",
                                    token = token
                                )
                                showDialog = false

                            },
                            modifier = Modifier
                                .size(35.dp)
                                .clip(CircleShape)
                                .border(
                                    0.5.dp,
                                    color = Color.Green,
                                    shape = CircleShape
                                )

                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Check,
                                contentDescription = "Interested button",
                                tint = Color.Green
                            )
                        }
                    }
                }
            }, backgroundColor = colorResource(R.color.black_modified)
        )
    }
}

@Composable
fun SkillItem(skill: String) {
    Text(
        text = skill,
        fontFamily = FontFamily(Font(R.font.josefin_sans_bold)),
        fontSize = 15.sp,
        modifier = Modifier
            .padding(4.dp)
            .background(Color.Gray.copy(alpha = 0.2f), shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 0.dp)
    )
}

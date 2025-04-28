package com.devlink.app


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.devlink.app.authentication.SigninResponse

data class BottomNavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)


@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier.zIndex(2f),
    navController: NavController,
    signinResponse: SigninResponse,
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val items = listOf(
        BottomNavItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            hasNews = false
        ),
        BottomNavItem(
            title = "Chat",
            selectedIcon = Icons.Filled.Email,
            unselectedIcon = Icons.Outlined.Email,
            hasNews = false,
            badgeCount = 23
        ),
        BottomNavItem(
            title = "Connections",
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
            hasNews = true
        ),
        BottomNavItem(
            title = "Gemini AI",
            selectedIcon = Icons.Filled.CheckCircle,
            unselectedIcon = Icons.Outlined.CheckCircle,
            hasNews = true
        )
    )

    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }

    NavigationBar(
        modifier = Modifier,
//        modifier = Modifier.height(screenHeight*0.1f),
        containerColor = Color.Black,

        ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
//                modifier = Modifier.align(Alignment.Bottom),
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    when (index) {
                        0 -> {}
                        1 -> navController.navigate(Screen.chat_screen + "/${signinResponse.token}")
                        2 -> navController.navigate(Screen.navigation_screen + "/${signinResponse.user.id}/${signinResponse.user.email}/${signinResponse.token}") {
                            popUpTo(Screen.login_screen) { inclusive = true }
                        }

                        3 -> navController.navigate(Screen.gemini_screen)
                    }
                },
                icon = {
                    if (selectedItemIndex == index) {
                        Icon(imageVector = item.selectedIcon, contentDescription = item.title)
                    } else {
                        Icon(imageVector = item.unselectedIcon, contentDescription = item.title)
                    }
                },
                label = {
                    Text(
                        item.title,
//                        style = TextStyle(fontSize = (screenWidth.value * 0.035).sp ),
                        fontFamily = FontFamily(Font(R.font.josefin_sans_bold))
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent,
                    selectedIconColor = colorResource(R.color.white),
                    unselectedIconColor = colorResource(R.color.white),
                    selectedTextColor = colorResource(R.color.white),
                    unselectedTextColor = colorResource(R.color.white),
                )


            )

        }
//        when (selectedItemIndex) {
////            0 -> HomeScreenView()
////            1 -> HomeScreenView()
////            2 -> HomeScreenView()
//            3 -> navController.navigate(Screen.gemini_screen)
//        }
    }

}
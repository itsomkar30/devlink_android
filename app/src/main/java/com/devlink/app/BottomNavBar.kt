package com.devlink.app

import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

data class BottomNavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)


@Composable
fun BottomNavBar(modifier: Modifier = Modifier.zIndex(2f)) {

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
            title = "Profile",
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
            hasNews = true
        )
    )

    var selectedItemIndex by rememberSaveable { mutableStateOf(0) }
    NavigationBar(
        modifier = Modifier.height(70.dp),
        containerColor = Color.Transparent  // Transparent background
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index  // Corrected assignment
                },
                icon = {
                    if (selectedItemIndex == index) {
                        Icon(imageVector = item.selectedIcon, contentDescription = item.title)
                    } else {
                        Icon(imageVector = item.unselectedIcon, contentDescription = item.title)
                    }
                },
                label = { Text(item.title, fontFamily = FontFamily(Font(R.font.josefin_sans_bold))) },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent ,
                    selectedIconColor =  colorResource(R.color.white),
                    unselectedIconColor =  colorResource(R.color.white),
                    selectedTextColor =  colorResource(R.color.white),
                    unselectedTextColor =  colorResource(R.color.white),
                )

            )
        }
    }
}
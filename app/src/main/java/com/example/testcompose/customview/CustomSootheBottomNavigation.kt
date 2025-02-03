package com.example.testcompose.customview

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.testcompose.R
import com.example.testcompose.utils.NavItemData
import com.example.testcompose.utils.Navigation

@Composable
fun CustomSootheBottomNavigation(
    modifier: Modifier = Modifier,
    navItems: List<NavItemData> = Navigation.ItemList
) {
    var currentNavIndex by rememberSaveable { mutableStateOf(0) }
    NavigationBar(
        modifier = modifier,
        contentColor = colorResource(id =  R.color.a11y_red_200)
    ) {
        navItems?.forEachIndexed { index, navItemData ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = navItemData.itemImg,
                        contentDescription = null
                    )
                },
                label = { TextView(name = navItemData.itemName) },
                selected = currentNavIndex == index,
                onClick = {
                    currentNavIndex = index
                }
            )
        }
    }
}

@Composable
fun BottomNavigation() {
    NavigationBar(
        modifier = Modifier,
        contentColor = colorResource(id =  R.color.a11y_red_200)
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null
                )
            },
            label = { TextView(name = "Home") },
            selected = true,
            onClick = {}
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null
                )
            },
            label = { TextView(name = "Profile") },
            selected = false,
            onClick = {}
        )
    }
}


@Preview
@Composable
fun PreviewSootheBottomNavigation() {
    CustomSootheBottomNavigation()
}

@Preview
@Composable
fun PreviewNavigation() {
    BottomNavigation()
}
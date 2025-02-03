package com.example.testcompose.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star

object Navigation {
    val ItemList: List<NavItemData> = listOf(
        NavItemData("Home", Icons.Default.Star),
        NavItemData("Profile", Icons.Default.Person)
    )
}
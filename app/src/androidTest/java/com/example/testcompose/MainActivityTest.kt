package com.example.testcompose

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.Matchers.`is`
import android.util.Log

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun customTextFieldView_isDisplayed() {
        Log.d("TestLog", "開始測試 customTextFieldView 是否顯示")
        println("開始測試 customTextFieldView 是否顯示")
        composeTestRule
            .onNodeWithTag("searchTextField")
            .assertIsDisplayed()
        Log.d("TestLog", "customTextFieldView 已顯示")
        println("customTextFieldView 已顯示")
    }

    @Test
    fun firstFavoriteCollection_showsKnightText() {
        Log.d("TestLog", "開始測試第一個 favoriteCollection 是否顯示 Knight")
        println("開始測試第一個 favoriteCollection 是否顯示 Knight")
        composeTestRule
            .onAllNodesWithText("Knight")[1] // 第二個 "Knight"，通常第一個在 alignYourBodyData，第二個在 favoriteCollectionData
            .assertIsDisplayed()
        Log.d("TestLog", "Knight 已顯示於第一個 favoriteCollection")
        println("Knight 已顯示於第一個 favoriteCollection")
    }

    @Test
    fun firstFavoriteCollection_showsReynoidText() {
        Log.d("TestLog", "開始測試 favoriteCollection 是否顯示 Reynold")
        println("開始測試 favoriteCollection 是否顯示 Reynold")
        composeTestRule
            .onAllNodesWithText("Reynoid")[1]
            .assertIsDisplayed()
        Log.d("TestLog", "Reynoid 已顯示於 favoriteCollection")
        println("Reynoid 已顯示於第一個 favoriteCollection")
    }
}

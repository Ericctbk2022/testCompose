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

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun customTextFieldView_isDisplayed() {
        composeTestRule
            .onNodeWithTag("searchTextField")
            .assertIsDisplayed()
    }

    @Test
    fun firstFavoriteCollection_showsKnightText() {
        // 檢查 LazyFavoriteCollectionByFavoriteData 第一個 item 是否為 "Knight"
        composeTestRule
            .onAllNodesWithText("Knight")[1] // 第二個 "Knight"，通常第一個在 alignYourBodyData，第二個在 favoriteCollectionData
            .assertIsDisplayed()
    }

    @Test
    fun firstFavoriteCollection_showsReynoidText() {
        // 檢查 favoriteCollectionData 的 "Reynoid"（第二個出現的 "Reynoid"）
        composeTestRule
            .onAllNodesWithText("Reynoid")[1]
            .assertIsDisplayed()
    }
}

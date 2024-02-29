package com.sgg.cinematics.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import com.sgg.cinematics.BaseTest
import com.sgg.cinematics.R
import com.sgg.cinematics.ui.components.NavItemVariant
import com.sgg.cinematics.utils.Destination
import com.sgg.cinematics.utils.MovieListUiMode
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test

@HiltAndroidTest
class CinematicsAppTest : BaseTest() {

    @Test
    fun test_fab_view_switch_with_default_carousel() {
        val fabSwitchViewTestTag = composeRule.activity.getString(R.string.test_tag_fab_view_switch)
        composeRule.onNodeWithTag(testTag = fabSwitchViewTestTag, useUnmergedTree = true)
            .assertIsDisplayed()
    }

    @Test
    fun test_click_fab_view_switch_carousel_list_and_list_carousel() {
        val fabSwitchViewTestTag = composeRule.activity.getString(R.string.test_tag_fab_view_switch)

        composeRule.onNodeWithTag(testTag = fabSwitchViewTestTag)
            .performClick()
        composeRule.onNodeWithTag(
            testTag = R.drawable.view_list_32.toString(),
            useUnmergedTree = true)
            .assertIsDisplayed()
        composeRule.onNodeWithTag(testTag = MovieListUiMode.CarouselView.testTag)
            .assertIsDisplayed()

        composeRule.onNodeWithTag(testTag = fabSwitchViewTestTag)
            .performClick()
        composeRule.onNodeWithTag(
            testTag = R.drawable.view_carousel_32.toString(),
            useUnmergedTree = true)
            .assertIsDisplayed()
        composeRule.onNodeWithTag(testTag = MovieListUiMode.ListView.testTag)
            .assertIsDisplayed()
    }

    @Test
    fun test_navigate_to_detail_screen_and_add_to_watch_list_button() {
        val addToWatchListTxt = composeRule.activity.getString(R.string.txt_add_to_watch_btn)
        val removeToWatchListTxt = composeRule.activity.getString(R.string.txt_remove_to_watch_btn)
        val testTagButton = composeRule.activity.getString(R.string.test_tag_button)

        navigateToDetailsScreen()

        composeRule.onNodeWithContentDescription(Destination.DetailScreen.testTag)
            .assertIsDisplayed()
        composeRule.onNodeWithText(addToWatchListTxt)
            .performScrollTo()
        composeRule.onNodeWithText(addToWatchListTxt)
            .assertIsDisplayed()
        composeRule.onNodeWithTag(testTagButton)
            .performClick()
        composeRule.waitForIdle()
        composeRule.onNodeWithText(removeToWatchListTxt)
            .assertExists()
    }

    @Test
    fun test_details_screen_without_bottom_nav_and_fab_switch_btn() {
        val bottomNavTestTag = composeRule.activity.getString(R.string.test_tag_bottom_nav)
        val fabViewSwitchTestTag = composeRule.activity.getString(R.string.test_tag_fab_view_switch)
        navigateToDetailsScreen()
        composeRule.onNodeWithTag(bottomNavTestTag)
            .assertDoesNotExist()
        composeRule.onNodeWithTag(testTag = fabViewSwitchTestTag, useUnmergedTree = true)
            .assertDoesNotExist()
    }

    @Test
    fun test_display_login_in_profile_screen_when_no_user_connected() {
        val content = composeRule.activity.getString(NavItemVariant.UserProfile.iconContentDescription)
        val loginScreenTestTag = composeRule.activity.getString(R.string.test_tag_login_screen)
        composeRule.onNodeWithContentDescription(label = content, useUnmergedTree = true)
            .performClick()
        composeRule.onNodeWithTag(loginScreenTestTag)
            .assertIsDisplayed()
    }

    private fun navigateToDetailsScreen() {
        val cardTestTag = composeRule.activity.getString(R.string.test_tag_card)
        composeRule.onAllNodesWithTag(cardTestTag, useUnmergedTree = true)[0].performClick()
    }

}
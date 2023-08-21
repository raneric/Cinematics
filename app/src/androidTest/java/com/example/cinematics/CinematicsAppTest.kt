package com.example.cinematics

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import com.example.cinematics.data.TestMovieRepository
import com.example.cinematics.ui.MainViewModel
import com.example.cinematics.ui.content.CinematicsAppScreen
import com.example.cinematics.utils.Destination
import com.example.cinematics.utils.UiState
import org.junit.Before
import org.junit.Test

class CinematicsAppTest : BaseTest() {

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        val movieRepository = TestMovieRepository()
        viewModel = MainViewModel(movieRepository)
        rule.setContent {
            CinematicsAppScreen(viewModel = viewModel)
        }
    }

    @Test
    fun test_fab_view_switch_with_default_carousel() {
        rule.onNodeWithTag(testTag = R.drawable.view_carousel_32.toString(),
                           useUnmergedTree = true)
                .assertIsDisplayed()
    }

    @Test
    fun test_click_fab_view_switch_carousel_list_and_list_carousel() {

        rule.onNodeWithTag(testTag = R.drawable.view_carousel_32.toString(),
                           useUnmergedTree = true)
                .performClick()
        rule.onNodeWithTag(testTag = R.drawable.view_list_32.toString(),
                           useUnmergedTree = true)
                .assertIsDisplayed()
        rule.onNodeWithTag(testTag = UiState.CarouselView.testTag)
                .assertIsDisplayed()

        rule.onNodeWithTag(testTag = R.drawable.view_list_32.toString(),
                           useUnmergedTree = true)
                .performClick()
        rule.onNodeWithTag(testTag = R.drawable.view_carousel_32.toString(),
                           useUnmergedTree = true)
                .assertIsDisplayed()
        rule.onNodeWithTag(testTag = UiState.ListView.testTag)
                .assertIsDisplayed()
    }

    @Test
    fun test_detail_screen_without_bottom_nav_and_add_to_watch_list_button() {
        val cardTestTag = rule.activity.getString(R.string.test_tag_card)
        val addToWatchListTxt = rule.activity.getString(R.string.txt_add_to_watch_btn)
        val removeToWatchListTxt = rule.activity.getString(R.string.txt_remove_to_watch_btn)
        val testTagButton = rule.activity.getString(R.string.test_tag_button)

        rule.onAllNodesWithTag(cardTestTag, useUnmergedTree = true)[0].performClick()
        rule.onNodeWithContentDescription(Destination.DetailScreen.testTag)
                .assertIsDisplayed()
        rule.onNodeWithText(addToWatchListTxt)
                .performScrollTo()
        rule.onNodeWithText(addToWatchListTxt)
                .assertIsDisplayed()
        rule.onNodeWithTag(testTagButton)
                .performClick()
        rule.waitForIdle()
        rule.onNodeWithText(removeToWatchListTxt)
                .assertExists()

    }
}
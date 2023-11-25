package com.sgg.cinematics

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import com.sgg.cinematics.data.TestMovieRepository
import com.sgg.cinematics.data.repository.impl.UiStatePreferencesRepositoryImpl
import com.sgg.cinematics.ui.MainViewModel
import com.sgg.cinematics.ui.screen.CinematicsAppScreen
import com.sgg.cinematics.utils.Destination
import com.sgg.cinematics.utils.MovieListUiMode
import org.junit.Before
import org.junit.Test

class CinematicsAppTest : BaseTest() {

    private lateinit var viewModel: MainViewModel

    private lateinit var uiStatePrefRepo: UiStatePreferencesRepositoryImpl

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Before
    fun setup() {
        val movieRepository = TestMovieRepository()
        uiStatePrefRepo = UiStatePreferencesRepositoryImpl(testDataStore)
        viewModel = MainViewModel(movieRepository)
        rule.setContent {
            val windowWIdth = calculateWindowSizeClass(activity = rule.activity)
            CinematicsAppScreen(windowsWidthSizeClass = windowWIdth.widthSizeClass)
        }
    }

    @Test
    fun test_fab_view_switch_with_default_carousel() {
        val fabSwitchViewTestTag = rule.activity.getString(R.string.test_tag_fab_view_switch)
        rule.onNodeWithTag(testTag = fabSwitchViewTestTag, useUnmergedTree = true)
                .assertIsDisplayed()
    }

    @Test
    fun test_click_fab_view_switch_carousel_list_and_list_carousel() {
        val fabSwitchViewTestTag = rule.activity.getString(R.string.test_tag_fab_view_switch)

        rule.onNodeWithTag(testTag = fabSwitchViewTestTag)
                .performClick()
        rule.onNodeWithTag(testTag = R.drawable.view_list_32.toString(), useUnmergedTree = true)
                .assertIsDisplayed()
        rule.onNodeWithTag(testTag = MovieListUiMode.CarouselView.testTag)
                .assertIsDisplayed()

        rule.onNodeWithTag(testTag = fabSwitchViewTestTag)
                .performClick()
        rule.onNodeWithTag(testTag = R.drawable.view_carousel_32.toString(), useUnmergedTree = true)
                .assertIsDisplayed()
        rule.onNodeWithTag(testTag = MovieListUiMode.ListView.testTag)
                .assertIsDisplayed()
    }

    @Test
    fun test_navigate_to_detail_screen_and_add_to_watch_list_button() {
        val addToWatchListTxt = rule.activity.getString(R.string.txt_add_to_watch_btn)
        val removeToWatchListTxt = rule.activity.getString(R.string.txt_remove_to_watch_btn)
        val testTagButton = rule.activity.getString(R.string.test_tag_button)

        navigateToDetailsScreen()

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

    @Test
    fun test_details_screen_without_bottom_nav_and_fab_switch_btn() {
        val bottomNavTestTag = rule.activity.getString(R.string.test_tag_bottom_nav)
        val fabViewSwitchTestTag = rule.activity.getString(R.string.test_tag_fab_view_switch)
        navigateToDetailsScreen()
        rule.onNodeWithTag(bottomNavTestTag)
                .assertDoesNotExist()
        rule.onNodeWithTag(testTag = fabViewSwitchTestTag, useUnmergedTree = true)
                .assertDoesNotExist()
    }

    private fun navigateToDetailsScreen() {
        val cardTestTag = rule.activity.getString(R.string.test_tag_card)
        rule.onAllNodesWithTag(cardTestTag, useUnmergedTree = true)[0].performClick()
    }

}
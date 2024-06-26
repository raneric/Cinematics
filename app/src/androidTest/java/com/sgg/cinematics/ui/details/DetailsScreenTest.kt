package com.sgg.cinematics.ui.details

import androidx.compose.ui.test.onNodeWithText
import com.sgg.cinematics.BaseTest
import com.sgg.cinematics.R
import com.sgg.cinematics.data.fakeMovieList
import com.sgg.cinematics.ui.screen.details.DetailsContent
import org.junit.Test

class DetailsScreenTest : BaseTest() {

    @Test
    fun details_screen_with_movie_not_in_watch_list() {
        val addToWatchListButtonText = composeRule.activity.getString(R.string.txt_add_to_watch_btn)
        composeRule.setContent {
            DetailsContent(movie = fakeMovieList[0],
                           isInWatchList = false,
                           onRecommendationItemClicked = {},
                           addOrRemoveWatchList = { /*TODO*/ })
        }
        composeRule.onNodeWithText(addToWatchListButtonText)
            .assertExists()
    }

    @Test
    fun details_screen_with_movie_in_watch_list() {
        val removeToWatchListButtonText =
            composeRule.activity.getString(R.string.txt_remove_to_watch_btn)
        composeRule.setContent {
            DetailsContent(movie = fakeMovieList[0],
                           isInWatchList = true,
                           onRecommendationItemClicked = {},
                           addOrRemoveWatchList = { /*TODO*/ })
        }
        composeRule.onNodeWithText(removeToWatchListButtonText)
            .assertExists()
    }
}
package com.sgg.cinematics

import androidx.compose.ui.test.onNodeWithText
import com.sgg.cinematics.data.movieList
import com.sgg.cinematics.ui.screen.details.DetailsContent
import org.junit.Test

class DetailsScreenTest : BaseTest() {

    @Test
    fun details_screen_with_movie_not_in_watch_list() {
        val addToWatchListButtonText = composeRule.activity.getString(R.string.txt_add_to_watch_btn)
        composeRule.setContent {
            DetailsContent(movie = movieList[0],
                           isInWatchList = false,
                           onRecommendationItemClicked = {},
                           addOrRemoveWatchList = { /*TODO*/ })
        }
        composeRule.onNodeWithText(addToWatchListButtonText)
            .assertExists()
    }

    @Test
    fun details_screen_with_movie_in_watch_list() {
        val removeToWatchListButtonText = composeRule.activity.getString(R.string.txt_remove_to_watch_btn)
        composeRule.setContent {
            DetailsContent(movie = movieList[0],
                           isInWatchList = true,
                           onRecommendationItemClicked = {},
                           addOrRemoveWatchList = { /*TODO*/ })
        }
        composeRule.onNodeWithText(removeToWatchListButtonText)
            .assertExists()
    }
}
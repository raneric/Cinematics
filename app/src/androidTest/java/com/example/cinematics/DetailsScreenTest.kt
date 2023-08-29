package com.example.cinematics


import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import com.example.cinematics.data.movieList
import com.example.cinematics.ui.content.DetailsContent
import com.example.cinematics.utils.Destination
import org.junit.Test

class DetailsScreenTest : BaseTest() {

    @Test
    fun details_screen_with_movie_not_in_watch_list() {
        val addToWatchListButtonText = rule.activity.getString(R.string.txt_add_to_watch_btn)
        rule.setContent {
            DetailsContent(movie = movieList[0],
                           isInWatchList = false,
                           onRecommendationItemClicked = {},
                           addOrRemoveWatchList = { /*TODO*/ })
        }
        rule.onNodeWithText(addToWatchListButtonText)
                .assertExists()
    }

    @Test
    fun details_screen_with_movie_in_watch_list() {
        val removeToWatchListButtonText = rule.activity.getString(R.string.txt_remove_to_watch_btn)
        rule.setContent {
            DetailsContent(movie = movieList[0],
                           isInWatchList = true,
                           onRecommendationItemClicked = {},
                           addOrRemoveWatchList = { /*TODO*/ })
        }
        rule.onNodeWithText(removeToWatchListButtonText)
                .assertExists()
    }
}
package com.example.cinematics

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.cinematics.data.movieList
import com.example.cinematics.ui.content.DetailsContent
import org.junit.Rule
import org.junit.Test

class DetailsScreenTest {

    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()

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
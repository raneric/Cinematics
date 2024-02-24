package com.sgg.cinematics.ui.movieList

import androidx.compose.ui.test.onNodeWithText
import com.sgg.cinematics.BaseTest
import com.sgg.cinematics.R
import com.sgg.cinematics.ui.screen.movieList.VerticalMovieListScreen
import org.junit.Test

class TrendingListTest : BaseTest() {

    @Test
    fun test_empty_movies_list_screen() {
        val emptyListText = composeRule.activity.getString(R.string.txt_no_item_found)
        composeRule.setContent {
            VerticalMovieListScreen(movieList = emptyList()) {

            }
        }
        composeRule.onNodeWithText(emptyListText)
            .assertExists()
    }
}
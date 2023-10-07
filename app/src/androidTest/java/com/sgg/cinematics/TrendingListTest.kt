package com.sgg.cinematics

import androidx.compose.ui.test.onNodeWithText
import com.sgg.cinematics.ui.screen.VerticalMovieListScreen
import org.junit.Test

class TrendingListTest : BaseTest() {

    @Test
    fun test_empty_movies_list_screen() {
        val emptyListText = rule.activity.getString(R.string.txt_no_item_found)
        rule.setContent {
            VerticalMovieListScreen(movieList = emptyList()) {

            }
        }
        rule.onNodeWithText(emptyListText)
                .assertExists()
    }
}
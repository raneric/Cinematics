package com.example.cinematics

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.cinematics.ui.content.VerticalMovieListScreen
import org.junit.Rule
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
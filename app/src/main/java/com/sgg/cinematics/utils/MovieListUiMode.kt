package com.sgg.cinematics.utils

import androidx.annotation.DrawableRes
import com.sgg.cinematics.R

sealed class MovieListUiMode(
    @DrawableRes val fabIcon: Int,
    val testTag: String
) {

    fun switch() = when (this) {
        ListView     -> CarouselView
        CarouselView -> ListView
    }

    object ListView : MovieListUiMode(fabIcon = R.drawable.view_carousel_32,
                                      testTag = "list_view_mode"
    )

    object CarouselView : MovieListUiMode(fabIcon = R.drawable.view_list_32,
                                          testTag = "carousel_view_mode"
    )

}
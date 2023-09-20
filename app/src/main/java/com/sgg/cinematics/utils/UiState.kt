package com.sgg.cinematics.utils

import androidx.annotation.DrawableRes
import com.sgg.cinematics.R

sealed class UiState(@DrawableRes val fabIcon: Int,
                     val testTag: String) {

    object ListView : UiState(fabIcon = R.drawable.view_carousel_32,
                              testTag = "list_view_mode")

    object CarouselView : UiState(fabIcon = R.drawable.view_list_32,
                                  testTag = "carousel_view_mode")

}
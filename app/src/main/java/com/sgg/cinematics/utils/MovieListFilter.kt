package com.sgg.cinematics.utils

import androidx.annotation.StringRes
import com.sgg.cinematics.R

enum class MovieListFilter(@StringRes val labelStringResId: Int) {
    TRENDING(labelStringResId = R.string.label_trending_radio_button),
    NEWEST(labelStringResId = R.string.label_newest_radio_button),
    TOP_RATED(labelStringResId = R.string.label_top_rated_radio_button)
}
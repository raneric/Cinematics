package com.example.cinematics.data

import androidx.annotation.DrawableRes

data class MovieModel(
        val id: Int,
        val title: String,
        val year: Int,
        val duration: String,
        val genres: List<String>,
        val ratingNote: Double,
        val stars: Int,
        var watched: Boolean = false,
        @DrawableRes val picture: Int,
        val author: String,
)
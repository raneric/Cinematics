package com.example.cinematics.data.model

import androidx.annotation.DrawableRes

data class MovieModel(
        val id: Int,
        val title: String,
        val year: Int,
        val duration: Double,
        val genres: List<String>,
        val ratingNote: Double,
        val stars: Int,
        @DrawableRes val picture: Int,
        val author: String,
        var overview: String = "N/A",
        var watched: Boolean = false,
) {
    val displayedDuration: String
        get() {
            return duration.toDisplayedDuration()
        }
}

private fun Double.toDisplayedDuration(): String {
    val hour = this.toInt()
    val min = ((this - hour) * 60).toInt()
    var result = StringBuilder()
    result.append(if (hour >= 10) hour.toString() else "0${hour}")
            .append("h:${min}")
    return result.toString()
}
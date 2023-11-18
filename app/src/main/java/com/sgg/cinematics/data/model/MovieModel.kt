package com.sgg.cinematics.data.model

import kotlin.reflect.full.declaredMemberProperties

data class MovieModel(
        val id: Int,
        val title: String,
        val year: Int,
        val duration: Double,
        val genres: List<String>,
        val ratingNote: Double,
        val stars: Int,
        val picture: String,
        val author: String,
        var overview: String = "N/A",
        var watched: Boolean = false,
) {
    val displayedDuration: String
        get() {
            return duration.toDisplayedDuration()
        }

    fun convertToMap(): Map<String, Any?> {
        val result = emptyMap<String, Any?>().toMutableMap()
        for (property in this::class.declaredMemberProperties) {
            result[property.name] = property.getter.call(this)
        }
        return result
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
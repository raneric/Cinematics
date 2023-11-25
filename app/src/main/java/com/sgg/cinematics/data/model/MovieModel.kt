package com.sgg.cinematics.data.model

import kotlin.reflect.full.declaredMemberProperties


data class MovieModel(
        val id: Int = 0,
        val title: String = "",
        val year: Int = 0,
        val duration: Double = 0.0,
        val genres: List<String> = emptyList(),
        val ratingNote: Double = 0.0,
        val stars: Int = 0,
        val picture: String = "",
        val author: String = "",
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
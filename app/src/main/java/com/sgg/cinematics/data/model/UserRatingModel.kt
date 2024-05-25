package com.sgg.cinematics.data.model

import androidx.annotation.DrawableRes
import java.time.LocalDate

data class UserRatingModel(
        val id: Int,
        val name: String,
        @DrawableRes val userPicture: Int,
        val rating: Int,
        val date: LocalDate
)
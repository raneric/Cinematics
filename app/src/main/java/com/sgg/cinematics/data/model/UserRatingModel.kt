package com.sgg.cinematics.data.model

import java.time.LocalDate

data class UserRatingModel(
        val id: Int,
        val userModel: UserModel,
        val rating: Int,
        val date: LocalDate)
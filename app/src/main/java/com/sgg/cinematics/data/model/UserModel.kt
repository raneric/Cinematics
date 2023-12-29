package com.sgg.cinematics.data.model

import androidx.annotation.DrawableRes
import java.time.LocalDate

data class UserModel(
        val id: Int,
        val name: String,
        val userName: String,
        val email: String,
        val address: String,
        val birthDate: LocalDate,
        val bio: String? = null,
        @DrawableRes val picture: Int,
)

data class AuthUser(
        val email: String,
        val password: String
)
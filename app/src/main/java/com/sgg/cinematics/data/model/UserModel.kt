package com.sgg.cinematics.data.model

import androidx.annotation.DrawableRes
import java.time.LocalDate

data class UserModel(
        val id: String,
        val firstName: String,
        val lastName: String,
        val userName: String,
        val email: String,
        val location: String? = null,
        val birthDate: LocalDate,
        val bio: String? = null,
        @DrawableRes val picture: Int,
) {
    val fullName
        get() = "$firstName $lastName"
}

data class AuthData(
        val uid: String? = null,
        val email: String = "",
        val password: String = ""
)

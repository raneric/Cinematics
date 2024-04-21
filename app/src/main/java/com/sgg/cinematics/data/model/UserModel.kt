package com.sgg.cinematics.data.model

import androidx.annotation.DrawableRes

data class UserModel(
        val id: String? = null,
        val firstName: String = "",
        val lastName: String = "",
        val email: String? = null,
        val location: String? = null,
        val birthDate: Long? = null,
        val bio: String? = null,
        val gender: String? = null,
        val pictureUrl: String? = null,
        @DrawableRes
        val picture: Int = 0,
) {
    val fullName
        get() = "$firstName $lastName"
}

data class AuthData(
        val uid: String? = null,
        val email: String = "",
        val password: String = ""
)

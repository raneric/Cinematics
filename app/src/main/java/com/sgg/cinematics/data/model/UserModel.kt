package com.sgg.cinematics.data.model

import androidx.annotation.DrawableRes
import java.time.LocalDate

data class UserModel(
    var id: String? = null,
    var firstName: String = "",
    var lastName: String = "",
    var email: String? = null,
    var location: String? = null,
    var birthDate: LocalDate? = null,
    var bio: String? = null,
    var gender: String? = null,
    var pictureUrl: String? = null,
    @DrawableRes
    var picture: Int = 0,
) {
    val fullName
        get() = "$firstName $lastName"
}

data class AuthData(
    val uid: String? = null,
    val email: String = "",
    val password: String = ""
)

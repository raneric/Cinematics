package com.sgg.cinematics.data.model

import com.sgg.cinematics.utils.millisToLocalDate

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
    val watchList: List<MovieModel> = emptyList()
) {
    val fullName
        get() = "$firstName $lastName"

    val displayedBirthDate
        get() = birthDate?.millisToLocalDate()
            .toString()
}

data class AuthData(
    val uid: String? = null,
    val email: String = "",
    val password: String = ""
)

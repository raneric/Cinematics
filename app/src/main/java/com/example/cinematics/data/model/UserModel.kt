package com.example.cinematics.data.model

import androidx.annotation.DrawableRes

data class UserModel(
        val id: Int,
        val name: String,
        val userName: String,
        @DrawableRes val picture: Int,
)

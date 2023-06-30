package com.example.cinematics.data

import androidx.annotation.DrawableRes

data class User(
        val name: String,
        val userName: String,
        @DrawableRes val picture: Int,
)

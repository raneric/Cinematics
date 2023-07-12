package com.example.cinematics.data

import androidx.annotation.DrawableRes

data class User(
        val id: Int,
        val name: String,
        val userName: String,
        @DrawableRes val picture: Int,
)

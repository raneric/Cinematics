package com.example.cinematics.data

import java.time.LocalDate
import java.util.Date
import kotlin.random.Random

data class UserRating(
        val id: Int,
        val user: User,
        val rating: Int,
        val date: LocalDate)
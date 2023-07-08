package com.example.cinematics.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")

val LocalDate.formatDate: String
    get() {
        return this.format(formatter)
    }

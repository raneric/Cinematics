package com.sgg.cinematics.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")

val LocalDate.formattedStringDate: String
    get() {
        return this.format(formatter)
    }

fun Long.millisToLocalDate(): LocalDate {
    return LocalDate.ofEpochDay(this / 1000 / 60 / 60 / 24)
}

fun LocalDate.toMillis(): Long {
    return this.toEpochDay() * 1000 * 60 * 60 * 24
}
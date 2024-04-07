package com.sgg.cinematics.utils

fun validateEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email)
        .matches()
}

fun validatePassword(password: String): Boolean {
    return PASSWORD_PATTERN.toRegex()
        .matches(password)
}

const val PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*[\\d])(?=.*[\\W_])[A-Za-z\\d\\W_]{8,}\$"
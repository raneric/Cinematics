package com.sgg.cinematics.utils

fun validateEmail(mail: String): Boolean {
    val emailRegex = ".+@{1}.+\\..+".toRegex()
    return emailRegex.matches(mail)
}
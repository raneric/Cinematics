package com.sgg.cinematics.domaine

object UserDataValidatorUseCase {

    fun validateEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email)
            .matches()
    }

    fun validatePassword(password: String): Boolean {
        return PASSWORD_PATTERN.toRegex()
            .matches(password)
    }

}

const val PASSWORD_PATTERN = ""
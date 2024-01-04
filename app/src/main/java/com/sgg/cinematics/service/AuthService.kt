package com.sgg.cinematics.service

import com.google.firebase.auth.FirebaseUser

interface AuthService {

    fun getConnectedUser(): FirebaseUser?

    suspend fun signInWithEmailAndPassword(
            email: String,
            password: String
    )

    fun signOut()
}
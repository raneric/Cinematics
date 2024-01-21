package com.sgg.cinematics.service

import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthService {

    val connectedUser: Flow<FirebaseUser?>

    suspend fun signInWithEmailAndPassword(
            email: String,
            password: String
    )

    fun signOut()
}
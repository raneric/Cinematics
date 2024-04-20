package com.sgg.cinematics.service

import com.google.firebase.auth.FirebaseUser
import com.sgg.cinematics.data.model.AuthData
import kotlinx.coroutines.flow.Flow

interface AuthService {

    val connectedUser: Flow<FirebaseUser?>

    suspend fun signInWithEmailAndPassword(
            email: String,
            password: String
    )

    suspend fun createUser(
            authData: AuthData
    ): FirebaseUser?

    fun signOut()
}
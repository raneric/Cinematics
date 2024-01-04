package com.sgg.cinematics.service.impl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.sgg.cinematics.service.AuthService
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthServiceImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) : AuthService {
    override fun getConnectedUser(): FirebaseUser? = firebaseAuth.currentUser


    override suspend fun signInWithEmailAndPassword(
            email: String,
            password: String
    ) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .await()
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }

}
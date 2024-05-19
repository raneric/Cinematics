package com.sgg.cinematics.service.impl

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.sgg.cinematics.data.model.AuthData
import com.sgg.cinematics.service.AuthService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthServiceImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) : AuthService {
    override val connectedUser: Flow<FirebaseUser?>
        get() = callbackFlow {
            val listener = FirebaseAuth.AuthStateListener { auth ->
                this.trySend(auth.currentUser)
            }
            firebaseAuth.addAuthStateListener(listener)
            awaitClose { firebaseAuth.removeAuthStateListener(listener) }
        }

    override suspend fun signInWithEmailAndPassword(
            email: String,
            password: String
    ) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                Log.d("LOGIN_DEBUG", "LOGIN SUCCESS with ${it.user?.email}")
            }
            .await()
    }

    override suspend fun createUser(authData: AuthData): FirebaseUser? {
        var authResult = firebaseAuth.createUserWithEmailAndPassword(authData.email,
                                                                     authData.password)
            .addOnFailureListener {
                throw AuthException("Failed to create user with email ${authData.email}")
            }
            .await()
        return authResult.user
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }
}

class AuthException(override val message: String) : Exception(message)
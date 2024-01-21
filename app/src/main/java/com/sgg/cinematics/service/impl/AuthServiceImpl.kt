package com.sgg.cinematics.service.impl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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
                .await()
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }

}
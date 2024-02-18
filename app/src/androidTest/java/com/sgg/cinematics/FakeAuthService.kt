package com.sgg.cinematics

import com.google.firebase.auth.FirebaseUser
import com.sgg.cinematics.service.AuthService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeAuthService() : AuthService {
    override val connectedUser: Flow<FirebaseUser?> = flowOf(null)

    override suspend fun signInWithEmailAndPassword(
            email: String,
            password: String
    ) {
        TODO("Not yet implemented")
    }

    override fun signOut() {
        TODO("Not yet implemented")
    }
}
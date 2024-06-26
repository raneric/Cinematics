package com.sgg.cinematics.service

import com.google.firebase.auth.FirebaseUser
import com.sgg.cinematics.data.model.AuthData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FakeAuthService @Inject constructor() : AuthService {
    override val connectedUser: Flow<FirebaseUser?> = flowOf(null)

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun createUser(authData: AuthData): FirebaseUser? {
        TODO("Not yet implemented")
    }

    override fun signOut() {
        TODO("Not yet implemented")
    }
}
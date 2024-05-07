package com.sgg.cinematics.ui


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.sgg.cinematics.service.AuthService
import com.sgg.cinematics.ui.components.NavItemVariant
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class MainViewModel @Inject constructor(
        private val authService: AuthService
) : ViewModel() {

    val connectedUser = authService.connectedUser

    var activeDestination: NavItemVariant by mutableStateOf(NavItemVariant.Trending)
        private set

    fun logout() {
        authService.signOut()
    }

    fun updateActiveDestination(destination: NavItemVariant) {
        activeDestination
    }
}


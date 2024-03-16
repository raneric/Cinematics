package com.sgg.cinematics.ui


import androidx.lifecycle.ViewModel
import com.sgg.cinematics.data.repository.MovieRepository
import com.sgg.cinematics.service.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class MainViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val authService: AuthService
) : ViewModel() {

    val connectedUser = authService.connectedUser

}


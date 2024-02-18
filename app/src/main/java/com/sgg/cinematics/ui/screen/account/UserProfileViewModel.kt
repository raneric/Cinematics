package com.sgg.cinematics.ui.screen.account

import com.sgg.cinematics.data.repository.MovieRepository
import com.sgg.cinematics.service.AuthService
import com.sgg.cinematics.ui.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
        private val repository: MovieRepository,
        private val authService: AuthService,
) :
        MainViewModel(repository, authService) {

    private var _stringDate = MutableStateFlow("yyyy-mm-dd")
    val stringDate
        get() = _stringDate
    
    fun updateStringDate(value: String) {

    }
}
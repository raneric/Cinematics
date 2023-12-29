package com.sgg.cinematics.ui.screen.login

import com.sgg.cinematics.data.repository.MovieRepository
import com.sgg.cinematics.ui.MainViewModel
import com.sgg.cinematics.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: MovieRepository) :
        MainViewModel(repository) {
    private var _loginUiState = MutableStateFlow<UiState>(UiState.Loading())
    val loginUiState = _loginUiState

}
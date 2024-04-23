package com.sgg.cinematics.ui.screen.login

import androidx.lifecycle.viewModelScope
import com.sgg.cinematics.data.model.AuthData
import com.sgg.cinematics.data.repository.MovieRepository
import com.sgg.cinematics.service.AuthService
import com.sgg.cinematics.ui.MainViewModel
import com.sgg.cinematics.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
        private val repository: MovieRepository,
        private val authService: AuthService
) : MainViewModel(repository, authService) {

    private var _loginUiState = MutableStateFlow<UiState?>(null)
    val loginUiState = _loginUiState

    private var _userLoginData = MutableStateFlow(AuthData())
    val userLoginData
        get() = _userLoginData.asStateFlow()

    private var _isEmailValid = MutableStateFlow(true)
    val isEmailValid
        get() = _isEmailValid

    fun updateEmail(mail: String) {
        val autUser = _userLoginData.value.copy(email = mail)
        _userLoginData.value = autUser
    }

    fun updatePassword(password: String) {
        val autUser = _userLoginData.value.copy(password = password)
        _userLoginData.value = autUser
    }

    fun login() {
        _loginUiState.value = UiState.Loading
        userLoginData.value?.let { user ->
            viewModelScope.launch {
                try {
                    authService.signInWithEmailAndPassword(
                            email = user.email,
                            password = user.password
                    )
                    _loginUiState.value = UiState.Success
                } catch (e: Exception) {
                    _loginUiState.value = UiState.Error(e.message.toString())
                }
            }
        }
    }

    fun logout() {
        authService.signOut()
    }
}
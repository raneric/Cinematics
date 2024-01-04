package com.sgg.cinematics.ui.screen.login

import androidx.lifecycle.viewModelScope
import com.sgg.cinematics.data.model.AuthUser
import com.sgg.cinematics.data.repository.MovieRepository
import com.sgg.cinematics.service.AuthService
import com.sgg.cinematics.ui.MainViewModel
import com.sgg.cinematics.utils.UiData
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
) : MainViewModel(repository) {
    private var _loginUiState = MutableStateFlow<UiState>(UiState.Loading())
    val loginUiState = _loginUiState

    private var _uiData = MutableStateFlow(UiData.LoginUiState(null))
    val uiDate
        get() = _uiData.asStateFlow()

    private var _isEmailValid = MutableStateFlow(true)
    val isEmailValid
        get() = _isEmailValid

    fun updateEmail(mail: String) {
        _isEmailValid.value = validateEmail(mail)
        val autUser = AuthUser(email = mail, password = uiDate.value.user?.password ?: "")
        _uiData.value = UiData.LoginUiState(autUser)
    }

    fun updatePassword(password: String) {
        val autUser = AuthUser(email = uiDate.value.user?.email ?: "", password = password)
        _uiData.value = UiData.LoginUiState(autUser)
    }

    fun validateEmail(mail: String): Boolean {
        val emailRegex = ".+@{1}.+\\..+".toRegex()
        return emailRegex.matches(mail)
    }

    fun login() {
        uiDate.value.user?.let { user ->
            viewModelScope.launch {
                authService.signInWithEmailAndPassword(email = user.email, password = user.password)
                authService.getConnectedUser()?.let {
                    _connectedUser?.value = it
                }
            }
        }
    }
}
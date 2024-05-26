package com.sgg.cinematics.ui.screen.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.sgg.cinematics.data.model.UserModel
import com.sgg.cinematics.data.repository.UserInfoRepository
import com.sgg.cinematics.service.AuthService
import com.sgg.cinematics.ui.MainViewModel
import com.sgg.cinematics.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
        private val userInfoRepository: UserInfoRepository,
        authService: AuthService
) : MainViewModel(authService) {

    private var _uiState = MutableStateFlow<UiState>(UiState.Init)
    val uiState
        get() = _uiState.asStateFlow()

    var user by mutableStateOf(UserModel())
        private set

    fun refreshUserInfo(uid: String) {
        _uiState.value = UiState.Loading
        viewModelScope.launch() {
            user = userInfoRepository.getUserInfo(uid)
            _uiState.value = if (user == null) UiState.Error("Failed to fetch user Info") else UiState.Success
        }
    }
}
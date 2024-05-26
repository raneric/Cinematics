package com.sgg.cinematics.ui.screen.profile

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

    private var _user = MutableStateFlow<UserModel?>(null)
    val user
        get() = _user.asStateFlow()

    fun refreshUserInfo(uid: String) {
        _uiState.value = UiState.Loading
        viewModelScope.launch() {
            _user.emit(userInfoRepository.getUserInfo(uid))
            _uiState.value = if (user.value == null) UiState.Error("Failed to fetch user Info") else UiState.Success
        }
    }
}
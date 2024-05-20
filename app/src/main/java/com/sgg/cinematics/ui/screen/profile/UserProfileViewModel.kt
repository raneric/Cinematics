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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
        private val userInfoRepository: UserInfoRepository,
        authService: AuthService
) : MainViewModel(authService) {

    var uiState by mutableStateOf<UiState>(UiState.Init)
        private set

    var user by mutableStateOf(UserModel())
        private set

    fun refreshUserInfo(uid: String) {
        uiState = UiState.Loading
        viewModelScope.launch() {
            user = userInfoRepository.getUserInfo(uid)
            uiState = if (user == null) UiState.Error("Failed to fetch user Info") else UiState.Success
        }
    }
}
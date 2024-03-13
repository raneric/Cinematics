package com.sgg.cinematics.ui.screen.account

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.sgg.cinematics.data.model.AuthData
import com.sgg.cinematics.data.model.UserModel
import com.sgg.cinematics.data.repository.MovieRepository
import com.sgg.cinematics.service.AuthService
import com.sgg.cinematics.ui.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val authService: AuthService,
) :
        MainViewModel(repository, authService) {

    var userInfo by mutableStateOf(UserModel())
        private set

    var authData by mutableStateOf(AuthData())
        private set

    var profilePictureUri by mutableStateOf<Uri?>(null)
        private set

    fun updateUserInfo(user: UserModel) {
        userInfo = user
    }

    fun updateAuthData(authData: AuthData) {
        this.authData = authData
    }

    fun updateProfilePictureUri(uri: Uri) {
        profilePictureUri = uri
    }
}
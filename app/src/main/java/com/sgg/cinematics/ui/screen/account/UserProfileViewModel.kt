package com.sgg.cinematics.ui.screen.account

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sgg.cinematics.data.model.AuthData
import com.sgg.cinematics.data.model.UserModel
import com.sgg.cinematics.domain.CreateAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(private val createAccountUseCase: CreateAccountUseCase) :
        ViewModel() {

    var userInfo by mutableStateOf(UserModel())
        private set

    var authData by mutableStateOf(AuthData())
        private set

    var profilePictureUri by mutableStateOf<Uri>(Uri.EMPTY)
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

    fun createAccount() {
        val exeptionHandler = CoroutineExceptionHandler { _, t ->
            Log.d(UserProfileViewModel::class.simpleName, "coroutines error handler t.${t.message}")
        }
        viewModelScope.launch(exeptionHandler) {
            createAccountUseCase(userInfo = userInfo,
                                 authData = authData,
                                 pictureProfileUri = profilePictureUri)
        }
    }
}

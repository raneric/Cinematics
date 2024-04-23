package com.sgg.cinematics.ui.screen.profile

import androidx.lifecycle.ViewModel
import com.sgg.cinematics.data.repository.UserInfoRepository
import javax.inject.Inject

class UserProfileViewModel @Inject constructor(
        private val userInfoRepository: UserInfoRepository
) : ViewModel() {
}
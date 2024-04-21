package com.sgg.cinematics.service.impl

import com.sgg.cinematics.data.model.UserModel
import com.sgg.cinematics.di.RoomDataSource
import com.sgg.cinematics.service.UserInfoDataSource
import javax.inject.Inject

//TODO caching user info data to local database
@RoomDataSource
class RoomUserInfoDataSource @Inject constructor() : UserInfoDataSource {

    override fun getUserInfo(uid: String) {
        TODO("Not yet implemented")
    }

    override fun addUserInfo(userInfo: UserModel) {
        TODO("Not yet implemented")
    }

    override fun updateUserInfo(userInfo: UserModel) {
        TODO("Not yet implemented")
    }

    override fun deleteUserInfo(uid: String) {
        TODO("Not yet implemented")
    }
}
package com.sgg.cinematics.service.impl

import com.sgg.cinematics.data.model.UserModel
import com.sgg.cinematics.service.UserInfoDataSource


class RemoteUserInfoDataSource(
) : UserInfoDataSource {
    override fun getUserInfo(uid: String): UserModel {
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
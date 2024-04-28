package com.sgg.cinematics.service

import com.sgg.cinematics.data.model.UserModel

interface UserInfoDataSource {
    fun getUserInfo(uid: String): UserModel

    fun addUserInfo(userInfo: UserModel)

    fun updateUserInfo(userInfo: UserModel)

    fun deleteUserInfo(uid: String)
}
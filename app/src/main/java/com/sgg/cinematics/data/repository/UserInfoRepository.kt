package com.sgg.cinematics.data.repository

import com.sgg.cinematics.data.model.UserModel

interface UserInfoRepository {

    fun getUserInfo(uid: String)

    fun addUserInfo(userInfo: UserModel)

    fun updateUserInfo(userInfo: UserModel)

    fun deleteUserInfo(uid: String)
}
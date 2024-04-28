package com.sgg.cinematics.data.repository

import com.sgg.cinematics.data.model.UserModel

interface UserInfoRepository {

    suspend fun getUserInfo(uid: String): UserModel

    fun addUserInfo(userInfo: UserModel)

    fun updateUserInfo(userInfo: UserModel)

    fun deleteUserInfo(uid: String)
}
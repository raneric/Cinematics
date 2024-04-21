package com.sgg.cinematics.data.repository.impl

import com.sgg.cinematics.data.model.UserModel
import com.sgg.cinematics.data.repository.UserInfoRepository
import com.sgg.cinematics.di.RemoteDataSource
import com.sgg.cinematics.di.RoomDataSource
import com.sgg.cinematics.service.UserInfoDataSource
import javax.inject.Inject

const val USER_COLLECTION = "userInfo"


class UserInfoRepositoryImpl @Inject constructor(
        @RemoteDataSource private val remoteDataSource: UserInfoDataSource,
        @RoomDataSource private val localDataSource: UserInfoDataSource
) : UserInfoRepository {
    override fun getUserInfo(uid: String) {
        TODO("Not yet implemented")
    }

    //FIXE dada upload
    override fun addUserInfo(userInfo: UserModel) {
        remoteDataSource.addUserInfo(userInfo)
    }

    override fun updateUserInfo(userInfo: UserModel) {
        TODO("Not yet implemented")
    }

    override fun deleteUserInfo(uid: String) {
        TODO("Not yet implemented")
    }
}
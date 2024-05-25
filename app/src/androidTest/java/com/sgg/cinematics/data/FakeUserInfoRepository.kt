package com.sgg.cinematics.data

import com.sgg.cinematics.data.model.MovieModel
import com.sgg.cinematics.data.model.UserModel
import com.sgg.cinematics.data.repository.UserInfoRepository
import com.sgg.cinematics.utils.MovieListFilter

class FakeUserInfoRepository : UserInfoRepository {
    override suspend fun getUserInfo(uid: String): UserModel {
        TODO("Not yet implemented")
    }

    override fun addOrUpdateUserInfo(userInfo: UserModel) {
        TODO("Not yet implemented")
    }

    override fun deleteUserInfo(uid: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getWatchList(
            uid: String,
            movieListFilter: MovieListFilter
    ): List<MovieModel> {
        TODO("Not yet implemented")
    }

    override fun addToWatchList(movieModel: MovieModel) {
        TODO("Not yet implemented")
    }

    override suspend fun isInWatchList(
            uid: String,
            movieModel: MovieModel
    ): Boolean {
        TODO("Not yet implemented")
    }
}
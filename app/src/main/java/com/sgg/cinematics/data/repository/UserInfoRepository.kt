package com.sgg.cinematics.data.repository

import com.sgg.cinematics.data.model.MovieModel
import com.sgg.cinematics.data.model.UserModel
import com.sgg.cinematics.utils.MovieListFilter

interface UserInfoRepository {

    suspend fun getUserInfo(uid: String): UserModel

    fun addOrUpdateUserInfo(userInfo: UserModel)

    fun deleteUserInfo(uid: String)

    suspend fun getWatchList(
        uid: String,
        movieListFilter: MovieListFilter
    ): List<MovieModel>

    fun addToWatchList(movieModel: MovieModel)

    suspend fun isInWatchList(
        uid: String,
        movieModel: MovieModel
    ): Boolean
}
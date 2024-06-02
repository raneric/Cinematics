package com.sgg.cinematics.data

import com.sgg.cinematics.data.model.MovieModel
import com.sgg.cinematics.data.model.UserModel
import com.sgg.cinematics.data.repository.UserInfoRepository
import com.sgg.cinematics.utils.MovieListFilter
import com.sgg.cinematics.utils.toMillis
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeUserInfoRepository @Inject constructor() : UserInfoRepository {
    override suspend fun getUserInfo(uid: String): UserModel {
        return fakeUserInfo
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
        return fakeUserInfo.watchList
    }

    override fun addToWatchList(movieModel: MovieModel) {
        val data = fakeUserInfo.watchList.toMutableList()
        data.add(movieModel)
        fakeUserInfo = fakeUserInfo.copy(watchList = data)
    }

    override suspend fun isInWatchList(
        uid: String,
        movieModel: MovieModel
    ): Boolean {
        return fakeUserInfo.watchList.find { it.id == movieModel.id } != null
    }
}

private var fakeUserInfo = UserModel(
    id = "1213",
    firstName = "John",
    lastName = "Doe",
    email = "test@test.ts",
    location = "Antananarivo",
    birthDate = LocalDate.of(1989, 8, 23)
        .toMillis(),
    bio = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur eu ipsum nulla. Maecenas aliquam consequat dui non ullamcorper. Curabitur laoreet finibus facilisis. Morbi sit amet sollicitudin odio, at lobortis erat. Nunc eget arcu nunc. Quisque placerat metus tempus, varius lacus at, varius est. Maecenas auctor dui a eros malesuada congue.",
    gender = "Male",
    pictureUrl = null,
    watchList = mutableListOf()
)
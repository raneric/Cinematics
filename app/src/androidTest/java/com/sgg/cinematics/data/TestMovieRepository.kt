package com.sgg.cinematics.data

import com.sgg.cinematics.data.model.MovieModel
import com.sgg.cinematics.data.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TestMovieRepository @Inject constructor() : MovieRepository {

    override fun getAllMovies() = flowOf(testMovieList)

    override fun getTopRated(): Flow<List<MovieModel>> {
        val sortedList = testMovieList.sortedByDescending { it.ratingNote }
        return flowOf(sortedList)
    }

    override suspend fun getMovie(id: Int): MovieModel {
        return testMovieList.first { it.id == id }
    }
}
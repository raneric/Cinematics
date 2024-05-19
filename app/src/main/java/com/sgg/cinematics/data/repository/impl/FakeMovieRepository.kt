package com.sgg.cinematics.data.repository.impl

import com.sgg.cinematics.data.model.MovieModel
import com.sgg.cinematics.data.movieList
import com.sgg.cinematics.data.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeMovieRepository @Inject constructor() : MovieRepository {

    override fun getTrending() = flowOf(movieList)

    override fun getTopRated(): Flow<List<MovieModel>> {
        val sortedList = movieList.sortedByDescending { it.ratingNote }
        return flowOf(sortedList)
    }

    override suspend fun getMovie(id: Int): MovieModel {
        return movieList.first { it.id == id }
    }
}
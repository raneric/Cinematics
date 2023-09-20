package com.sgg.cinematics.data

import com.sgg.cinematics.data.model.MovieModel
import com.sgg.cinematics.data.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TestMovieRepository @Inject constructor() : MovieRepository {

    override fun getTrending() = flowOf(testMovieList)

    override fun getTopRated(): Flow<List<MovieModel>> {
        val sortedList = testMovieList.sortedByDescending { it.ratingNote }
        return flowOf(sortedList)
    }

    override fun getWatchList(): List<MovieModel> = testWatchList

    override fun addToWatchList(movie: MovieModel) {
        testWatchList.add(movie)
    }

    override fun removeToWatchList(movie: MovieModel) {
        testWatchList.remove(movie)
    }

    override fun findInWatchList(movie: MovieModel): Boolean {
        return testWatchList.contains(movie)
    }

    override fun getMovie(id: Int): MovieModel {
        return testMovieList.first { it.id == id }
    }
}
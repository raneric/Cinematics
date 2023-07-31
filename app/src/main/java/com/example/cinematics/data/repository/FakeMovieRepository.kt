package com.example.cinematics.data.repository

import com.example.cinematics.data.model.MovieModel
import com.example.cinematics.data.movieList
import com.example.cinematics.data.watchList
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

    override fun getWatchList(): Flow<List<MovieModel>> = flowOf(watchList)

    override fun addToWatchList(movie: MovieModel) {
        watchList.add(movie)
    }

    override fun removeToWatchList(movie: MovieModel) {
        watchList.remove(movie)
    }

    override fun findInWatchList(movie: MovieModel): Boolean {
        return watchList.contains(movie)
    }

    override fun getMovie(id: Int): MovieModel {
        return movieList.first { it.id == id }
    }
}
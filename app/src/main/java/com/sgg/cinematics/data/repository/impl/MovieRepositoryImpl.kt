package com.sgg.cinematics.data.repository.impl

import com.sgg.cinematics.data.datasource.MovieDataSource
import com.sgg.cinematics.data.model.MovieModel
import com.sgg.cinematics.data.repository.MovieRepository
import com.sgg.cinematics.data.watchList
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val dataSource: MovieDataSource) :
        MovieRepository {
    override fun getTrending(): Flow<List<MovieModel>> {
        return dataSource.getAllMovies()
    }

    override fun getTopRated(): Flow<List<MovieModel>> {
        return dataSource.getTopRatedMovies()
    }

    override fun getWatchList(): List<MovieModel> = watchList

    override fun addToWatchList(movie: MovieModel) {
        watchList.add(movie)
    }

    override fun removeToWatchList(movie: MovieModel) {
        watchList.remove(movie)
    }

    override fun findInWatchList(movie: MovieModel): Boolean {
        return watchList.contains(movie)
    }

    override suspend fun getMovie(id: Int): MovieModel? {
        return dataSource.getMovie(id)
    }
}
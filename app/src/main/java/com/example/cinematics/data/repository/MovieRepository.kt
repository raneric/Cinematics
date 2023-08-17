package com.example.cinematics.data.repository

import com.example.cinematics.data.model.MovieModel
import com.example.cinematics.data.movieList
import com.example.cinematics.data.watchList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface MovieRepository {

    fun getTrending(): Flow<List<MovieModel>>

    fun getTopRated(): Flow<List<MovieModel>>

    fun getWatchList(): List<MovieModel>
    fun addToWatchList(movie: MovieModel)

    fun removeToWatchList(movie: MovieModel)

    fun findInWatchList(movie: MovieModel): Boolean

    fun getMovie(id: Int): MovieModel
}
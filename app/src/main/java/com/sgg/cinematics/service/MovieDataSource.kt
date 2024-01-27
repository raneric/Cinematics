package com.sgg.cinematics.service

import com.sgg.cinematics.data.model.MovieModel
import kotlinx.coroutines.flow.Flow

interface MovieDataSource {

    fun getAllMovies(): Flow<List<MovieModel>>

    fun getTopRatedMovies(): Flow<List<MovieModel>>

    suspend fun getMovie(id: Int): MovieModel?
}
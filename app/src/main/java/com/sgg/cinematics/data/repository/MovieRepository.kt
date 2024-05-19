package com.sgg.cinematics.data.repository

import com.sgg.cinematics.data.model.MovieModel
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getTrending(): Flow<List<MovieModel>>

    fun getTopRated(): Flow<List<MovieModel>>

    suspend fun getMovie(id: Int): MovieModel?
}
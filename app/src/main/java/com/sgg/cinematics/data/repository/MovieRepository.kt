package com.sgg.cinematics.data.repository

import com.sgg.cinematics.data.model.MovieModel
import com.sgg.cinematics.utils.MovieListFilter
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getAllMovies(movieListFilter: MovieListFilter): Flow<List<MovieModel>>

    suspend fun getMovie(id: Int): MovieModel?
}
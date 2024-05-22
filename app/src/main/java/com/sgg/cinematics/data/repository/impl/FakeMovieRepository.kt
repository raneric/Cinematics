package com.sgg.cinematics.data.repository.impl

import com.sgg.cinematics.data.model.MovieModel
import com.sgg.cinematics.data.movieList
import com.sgg.cinematics.data.repository.MovieRepository
import com.sgg.cinematics.utils.MovieListFilter
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeMovieRepository @Inject constructor() : MovieRepository {

    override fun getAllMovies(movieListFilter: MovieListFilter) = flowOf(movieList)

    override suspend fun getMovie(id: Int): MovieModel {
        return movieList.first { it.id == id }
    }
}
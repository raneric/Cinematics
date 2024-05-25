package com.sgg.cinematics.data.repository.impl

import com.sgg.cinematics.data.fakeMovieList
import com.sgg.cinematics.data.model.MovieModel
import com.sgg.cinematics.data.repository.MovieRepository
import com.sgg.cinematics.utils.MovieListFilter
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeMovieRepository @Inject constructor() : MovieRepository {

    override fun getAllMovies(movieListFilter: MovieListFilter) = flowOf(fakeMovieList)

    override suspend fun getMovie(id: Int): MovieModel {
        return fakeMovieList.first { it.id == id }
    }
}
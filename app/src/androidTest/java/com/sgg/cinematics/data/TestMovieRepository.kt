package com.sgg.cinematics.data

import com.sgg.cinematics.data.model.MovieModel
import com.sgg.cinematics.data.repository.MovieRepository
import com.sgg.cinematics.utils.MovieListFilter
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TestMovieRepository @Inject constructor() : MovieRepository {

    override fun getAllMovies(movieListFilter: MovieListFilter): Flow<List<MovieModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovie(id: Int): MovieModel {
        return testMovieList.first { it.id == id }
    }
}
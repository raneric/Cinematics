package com.sgg.cinematics.data

import com.sgg.cinematics.data.model.MovieModel
import com.sgg.cinematics.data.repository.MovieRepository
import com.sgg.cinematics.utils.MovieListFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TestMovieRepository @Inject constructor() : MovieRepository {

    override fun getAllMovies(movieListFilter: MovieListFilter): Flow<List<MovieModel>> {
        return flow {
            emit(testMovieList)
        }
    }

    override suspend fun getMovie(id: Int): MovieModel {
        return testMovieList.first { it.id == id }
    }
}
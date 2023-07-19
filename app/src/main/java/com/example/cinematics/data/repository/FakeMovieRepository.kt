package com.example.cinematics.data.repository

import com.example.cinematics.data.model.MovieModel
import com.example.cinematics.data.movieList
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeMovieRepository @Inject constructor() {

    fun getAll() = flowOf(movieList)

    fun getMovie(id: Int): MovieModel {
        return movieList.first { it.id == id }
    }
}
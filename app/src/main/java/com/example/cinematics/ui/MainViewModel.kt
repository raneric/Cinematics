package com.example.cinematics.ui

import androidx.lifecycle.ViewModel
import com.example.cinematics.data.model.MovieModel
import com.example.cinematics.data.repository.FakeMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: FakeMovieRepository) : ViewModel() {
    private var _movies = repository.getAll()

    val movies: Flow<List<MovieModel>>
        get() = _movies

    fun getMovie(id: Int): MovieModel = repository.getMovie(id)
}
package com.example.cinematics.ui

import androidx.lifecycle.ViewModel
import com.example.cinematics.data.model.MovieModel
import com.example.cinematics.data.repository.FakeMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: FakeMovieRepository) : ViewModel() {
    private var _trendingMovies: Flow<List<MovieModel>> = repository.getTrending()
    val trendingMovies: Flow<List<MovieModel>>
        get() = _trendingMovies

    private var _topRatedMovies: Flow<List<MovieModel>> = repository.getTopRated()
    val topRatedMovies: Flow<List<MovieModel>>
        get() = _topRatedMovies

    private var _watchList: Flow<List<MovieModel>> = repository.getWatchList()
    val watchList: Flow<List<MovieModel>>
        get() = _watchList

    fun addToWatchList(movie: MovieModel) = repository.addToWatchList(movie)

    fun removeToWatchList(movie: MovieModel) = repository.removeToWatchList(movie)

    fun isInWatchList(movie: MovieModel) = repository.findInWatchList(movie)

    fun getMovie(id: Int): MovieModel = repository.getMovie(id)
}
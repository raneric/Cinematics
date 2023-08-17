package com.example.cinematics.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinematics.data.model.MovieModel
import com.example.cinematics.data.repository.FakeMovieRepository
import com.example.cinematics.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {
    private var _trendingMovies: Flow<List<MovieModel>> = repository.getTrending()
    val trendingMovies: Flow<List<MovieModel>>
        get() = _trendingMovies

    private var _topRatedMovies: Flow<List<MovieModel>> = repository.getTopRated()
    val topRatedMovies: Flow<List<MovieModel>>
        get() = _topRatedMovies

    private var _watchList: List<MovieModel> = repository.getWatchList()
    val watchList: List<MovieModel>
        get() = _watchList

    private var testBool: Boolean = false

    private var _isInWatchList: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isInWatchList
        get() = _isInWatchList

    fun addToWatchList(movie: MovieModel) {
        repository.addToWatchList(movie)
        _isInWatchList.value = true
    }

    fun removeToWatchList(movie: MovieModel) {
        repository.removeToWatchList(movie)
        _isInWatchList.value = false
    }

    fun getMovie(id: Int): MovieModel {
        val movie = repository.getMovie(id)
        _isInWatchList.value = repository.findInWatchList(movie)
        return movie
    }
}
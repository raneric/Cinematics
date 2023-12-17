package com.sgg.cinematics.ui


import androidx.lifecycle.ViewModel
import com.sgg.cinematics.data.model.MovieModel
import com.sgg.cinematics.data.repository.MovieRepository
import com.sgg.cinematics.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
open class MainViewModel @Inject constructor(private val repository: MovieRepository) :
        ViewModel() {

    private var _uiState = MutableStateFlow<UiState>(UiState.Loading())
    val uiState = _uiState.asStateFlow()

    private var _trendingMovies: Flow<List<MovieModel>> = repository.getTrending()
    val trendingMovies: Flow<List<MovieModel>>
        get() = _trendingMovies

    private var _topRatedMovies: Flow<List<MovieModel>> = repository.getTopRated()
    val topRatedMovies: Flow<List<MovieModel>>
        get() = _topRatedMovies

    private var _watchList: List<MovieModel> = repository.getWatchList()
    val watchList: List<MovieModel>
        get() = _watchList

    fun updateUiState(uiState: UiState) {
        _uiState.value = uiState
    }
}

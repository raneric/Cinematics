package com.sgg.cinematics.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sgg.cinematics.data.model.MovieModel
import com.sgg.cinematics.data.repository.MovieRepository
import com.sgg.cinematics.service.AuthService
import com.sgg.cinematics.utils.Destination
import com.sgg.cinematics.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class MainViewModel @Inject constructor(
        private val repository: MovieRepository,
        private val authService: AuthService
) : ViewModel() {

    private val _movies = MutableStateFlow<List<MovieModel>>(emptyList())
    val movies
        get() = _movies.asStateFlow()

    val connectedUser = authService.connectedUser

    private var _listUiState = MutableStateFlow<UiState>(UiState.Loading)
    val listUiState = _listUiState.asStateFlow()

    fun updateMovieList(destinationRoute: String) {
        _listUiState.value = UiState.Loading
        when (destinationRoute) {
            Destination.TopRatedScreen.route  -> {
                viewModelScope.launch {
                    repository.getTopRated()
                        .collectLatest {
                            _movies.emit(it)
                        }
                }
            }

            Destination.TrendingScreen.route  -> {
                viewModelScope.launch {
                    repository.getTrending()
                        .collectLatest {
                            _movies.emit(it)
                        }
                }
            }

            Destination.WatchListScreen.route -> {
                viewModelScope.launch {
                    _movies.emit(repository.getWatchList())
                }
            }
        }
        _listUiState.value = UiState.Success
    }
}


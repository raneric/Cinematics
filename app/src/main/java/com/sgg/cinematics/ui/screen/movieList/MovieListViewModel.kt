package com.sgg.cinematics.ui.screen.movieList

import androidx.lifecycle.viewModelScope
import com.sgg.cinematics.data.model.MovieModel
import com.sgg.cinematics.data.repository.MovieRepository
import com.sgg.cinematics.data.repository.PreferenceRepository
import com.sgg.cinematics.service.AuthService
import com.sgg.cinematics.ui.MainViewModel
import com.sgg.cinematics.utils.Destination
import com.sgg.cinematics.utils.MovieListUiMode
import com.sgg.cinematics.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class MovieListViewModel @Inject constructor(
        private val repository: MovieRepository,
        private val uiStateRepository: PreferenceRepository,
        authService: AuthService
) : MainViewModel(authService) {

    val uiListMode: Flow<MovieListUiMode> = uiStateRepository.movieListUiModeFlow

    private var _watchList: Flow<List<MovieModel>> = MutableStateFlow(emptyList())
    val watchList: Flow<List<MovieModel>>
        get() = _watchList

    private var _listUiState = MutableStateFlow<UiState>(UiState.Loading)
    val listUiState = _listUiState.asStateFlow()

    private val _movies = MutableStateFlow<List<MovieModel>>(emptyList())
    val movies
        get() = _movies.asStateFlow()

    init {
        updateMovieList(Destination.TrendingScreen.route)
    }

    fun updateMovieList(destinationRoute: String) {
        _listUiState.value = UiState.Loading
        when (destinationRoute) {

            Destination.TrendingScreen.route  -> {
                viewModelScope.launch {
                    repository.getTrending()
                        .collectLatest {
                            _movies.emit(it)
                        }
                }
            }

            Destination.WatchListScreen.route -> {
                TODO()
            }
        }
        _listUiState.value = UiState.Success
    }

    suspend fun switchListViewMode(movieListUiMode: MovieListUiMode) {
        uiStateRepository.updateUiState(movieListUiMode)
    }
}
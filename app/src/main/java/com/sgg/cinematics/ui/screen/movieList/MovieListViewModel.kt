package com.sgg.cinematics.ui.screen.movieList

import com.sgg.cinematics.data.model.MovieModel
import com.sgg.cinematics.data.repository.MovieRepository
import com.sgg.cinematics.data.repository.impl.UiStatePreferencesRepositoryImpl
import com.sgg.cinematics.service.AuthService
import com.sgg.cinematics.ui.MainViewModel
import com.sgg.cinematics.utils.Destination
import com.sgg.cinematics.utils.MovieListUiMode
import com.sgg.cinematics.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
        private val repository: MovieRepository,
        private val authService: AuthService,
        private val uiStateRepository: UiStatePreferencesRepositoryImpl
) : MainViewModel(repository, authService) {

    private var _listUiState = MutableStateFlow<UiState>(UiState.Loading)
    val listUiState = _listUiState.asStateFlow()

    val uiListMode: Flow<MovieListUiMode> = uiStateRepository.movieListUiModeFlow

    private var _movieList: Flow<List<MovieModel>> = repository.getTrending()
    val movieList: Flow<List<MovieModel>>
        get() = _movieList

    fun updateUiState(destinationRoute: String) {
        _listUiState.value = UiState.Loading
        when (destinationRoute) {
            Destination.TrendingScreen.route -> {
                _movieList = repository.getTrending()
            }

            Destination.TopRatedScreen.route -> {
                _movieList = repository.getTopRated()
            }
        }
        _listUiState.value = UiState.Success
    }

    suspend fun switchListViewMode(movieListUiMode: MovieListUiMode) {
        uiStateRepository.updateUiState(movieListUiMode)
    }
}
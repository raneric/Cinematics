package com.sgg.cinematics.ui.screen.movieList

import com.sgg.cinematics.data.model.MovieModel
import com.sgg.cinematics.data.repository.MovieRepository
import com.sgg.cinematics.data.repository.impl.UiStatePreferencesRepositoryImpl
import com.sgg.cinematics.service.AuthService
import com.sgg.cinematics.ui.MainViewModel
import com.sgg.cinematics.utils.MovieListUiMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
open class MovieListViewModel @Inject constructor(
        private val repository: MovieRepository,
        private val authService: AuthService,
        private val uiStateRepository: UiStatePreferencesRepositoryImpl
) : MainViewModel(repository, authService) {


    val uiListMode: Flow<MovieListUiMode> = uiStateRepository.movieListUiModeFlow

    private var _movieList: Flow<List<MovieModel>> = repository.getTrending()
    val movieList: Flow<List<MovieModel>>
        get() = _movieList

    suspend fun switchListViewMode(movieListUiMode: MovieListUiMode) {
        uiStateRepository.updateUiState(movieListUiMode)
    }
}
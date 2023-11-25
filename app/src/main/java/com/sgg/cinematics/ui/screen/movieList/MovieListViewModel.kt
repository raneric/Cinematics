package com.sgg.cinematics.ui.screen.movieList

import com.sgg.cinematics.data.repository.MovieRepository
import com.sgg.cinematics.data.repository.UiStatePreferencesRepository
import com.sgg.cinematics.ui.MainViewModel
import com.sgg.cinematics.utils.MovieListUiMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(private val repository: MovieRepository,
                                             private val uiStateRepository: UiStatePreferencesRepository) :
        MainViewModel(repository) {
    val uiListState: Flow<MovieListUiMode> = uiStateRepository.movieListUiModeFlow

    suspend fun switchListViewMode(movieListUiMode: MovieListUiMode) {
        uiStateRepository.updateUiState(movieListUiMode)
    }
}
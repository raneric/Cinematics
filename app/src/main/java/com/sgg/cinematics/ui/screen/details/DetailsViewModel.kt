package com.sgg.cinematics.ui.screen.details

import androidx.lifecycle.viewModelScope
import com.sgg.cinematics.data.model.MovieModel
import com.sgg.cinematics.data.repository.MovieRepository
import com.sgg.cinematics.service.AuthService
import com.sgg.cinematics.ui.MainViewModel
import com.sgg.cinematics.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
        private val repository: MovieRepository,
        private val authService: AuthService
) : MainViewModel(repository, authService) {

    private var _detailsUiState = MutableStateFlow<UiState>(UiState.Loading)
    val detailsUiState
        get() = _detailsUiState.asStateFlow()

    private var _isInWatchList: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isInWatchList
        get() = _isInWatchList


    private var _selectedMovie = MutableStateFlow<MovieModel?>(null)
    val selectedMovie
        get() = _selectedMovie.asStateFlow()

    fun addToWatchList(movie: MovieModel) {
        repository.addToWatchList(movie)
        _isInWatchList.value = true
    }

    fun removeToWatchList(movie: MovieModel) {
        repository.removeToWatchList(movie)
        _isInWatchList.value = false
    }

    fun updateUiState(id: Int) {
        _detailsUiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val result = repository.getMovie(id)
                _selectedMovie.value = result
                _detailsUiState.value = if (result == null) UiState.Error("Movie not found") else UiState.Success
            } catch (e: Exception) {
                _detailsUiState.value = UiState.Error(e.message!!)
            }
        }
    }
}
package com.sgg.cinematics.ui.screen.details

import androidx.lifecycle.viewModelScope
import com.sgg.cinematics.data.model.MovieModel
import com.sgg.cinematics.data.repository.MovieRepository
import com.sgg.cinematics.ui.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: MovieRepository) :
        MainViewModel(repository) {

    private var _isInWatchList: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private var _selectedMovie = MutableStateFlow<MovieModel?>(null)
    val selectedMovie = _selectedMovie.asStateFlow()

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

    fun updateSelectedMovie(id: Int) {
        viewModelScope.launch {
            repository.getMovie(id)
                    ?.let {
                        _selectedMovie.value = it
                    }
        }
    }
}
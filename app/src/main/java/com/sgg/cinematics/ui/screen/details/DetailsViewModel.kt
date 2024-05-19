package com.sgg.cinematics.ui.screen.details

import androidx.lifecycle.viewModelScope
import com.sgg.cinematics.data.model.MovieModel
import com.sgg.cinematics.data.repository.MovieRepository
import com.sgg.cinematics.data.repository.UserInfoRepository
import com.sgg.cinematics.service.AuthService
import com.sgg.cinematics.ui.MainViewModel
import com.sgg.cinematics.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
        private val movieRepository: MovieRepository,
        private val userInfoRepository: UserInfoRepository,
        authService: AuthService
) : MainViewModel(authService) {

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
        _isInWatchList.value = true
    }

    fun removeToWatchList(movie: MovieModel) {
        _isInWatchList.value = false
    }

    suspend fun updateUiState(id: Int) {
        _detailsUiState.value = UiState.Loading
        try {
            val result = movieRepository.getMovie(id)
            _selectedMovie.value = result
            _detailsUiState.value = UiState.Success
        } catch (e: Exception) {
            _detailsUiState.value = UiState.Error(e.message!!)
        }
    }

    fun addOrRemoveToWatchList(uid: String?) {
        val exeptionHandler = CoroutineExceptionHandler { _, throwable ->
            _detailsUiState.value = UiState.Error(throwable.message.toString())
        }
        when (uid) {
            null -> {
                TODO()
            }

            else -> {
                viewModelScope.launch(exeptionHandler) {
                    val user = userInfoRepository.getUserInfo(uid)
                    user?.apply {
                        val currentList = this.watchList.toMutableList()
                        currentList.add(selectedMovie.value!!)
                        val newUserInfo = this.copy(watchList = currentList)
                        userInfoRepository.addOrUpdateUserInfo(newUserInfo)
                    }
                }
            }
        }
    }
}
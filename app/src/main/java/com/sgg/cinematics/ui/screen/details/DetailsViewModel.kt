package com.sgg.cinematics.ui.screen.details

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
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

    var isInWatchList: MutableState<Boolean> = mutableStateOf(false)
        private set

    private var _selectedMovie = MutableStateFlow<MovieModel?>(null)
    val selectedMovie
        get() = _selectedMovie.asStateFlow()

    suspend fun loadMovieInfo(movieId: Int) {
        _detailsUiState.value = UiState.Loading
        try {
            val result = movieRepository.getMovie(movieId)
            _selectedMovie.value = result
            _detailsUiState.value = UiState.Success
        } catch (e: Exception) {
            _detailsUiState.value = UiState.Error(e.message!!)
        }
    }

    suspend fun loadIfInWatchList(
        uid: String?
    ) {
        if (uid != null && _selectedMovie.value != null) {
            isInWatchList.value = userInfoRepository.isInWatchList(uid, _selectedMovie.value!!)
        }
    }

    fun addOrRemoveToWatchList(uid: String) {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            _detailsUiState.value = UiState.Error(throwable.message.toString())
        }
        viewModelScope.launch(exceptionHandler) {
            val user = userInfoRepository.getUserInfo(uid)
            user?.run {
                val currentList = this.watchList.toMutableList()
                if (isInWatchList.value) {
                    currentList.removeIf { it.id == selectedMovie.value?.id }
                } else {
                    currentList.add(selectedMovie.value!!)
                }
                val newUserInfo = this.copy(watchList = currentList)
                userInfoRepository.addOrUpdateUserInfo(newUserInfo)
                isInWatchList.value = !isInWatchList.value
            }
        }
    }
}

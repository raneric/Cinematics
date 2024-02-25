package com.sgg.cinematics.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sgg.cinematics.data.model.MovieModel
import com.sgg.cinematics.data.repository.MovieRepository
import com.sgg.cinematics.service.AuthService
import com.sgg.cinematics.utils.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class MainViewModel @Inject constructor(
        private val repository: MovieRepository,
        private val authService: AuthService
) : ViewModel() {

    private var _topRatedMovies: Flow<List<MovieModel>> = repository.getTopRated()
    val topRatedMovies: Flow<List<MovieModel>>
        get() = _topRatedMovies

    private var _watchList: List<MovieModel> = repository.getWatchList()
    val watchList: List<MovieModel>
        get() = _watchList

    val movies = MutableStateFlow<List<MovieModel>>(emptyList())


    val connectedUser = authService.connectedUser

    fun updateMovieList(destinationRoute: String) {
        when (destinationRoute) {
            Destination.TopRatedScreen.route -> {
                viewModelScope.launch {
                    repository.getTopRated()
                        .collectLatest {
                            movies.emit(it)
                        }
                }
            }

            Destination.TrendingScreen.route -> {
                viewModelScope.launch {
                    repository.getTrending()
                        .collectLatest {
                            movies.emit(it)
                        }
                }
            }
        }

    }
}


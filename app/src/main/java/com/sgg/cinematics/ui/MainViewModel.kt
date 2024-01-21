package com.sgg.cinematics.ui


import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.sgg.cinematics.data.model.MovieModel
import com.sgg.cinematics.data.repository.MovieRepository
import com.sgg.cinematics.service.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
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

    protected var _connectedUser: MutableStateFlow<FirebaseUser?> = MutableStateFlow(null)
    val connectedUser
        get() = _connectedUser

    /*  private suspend fun loadConnectedUser() {
          val user = authService.connectedUser.stateIn(viewModelScope).value
          if (_connectedUser == null) {
              _connectedUser = MutableStateFlow(user)
          } else {
              _connectedUser?.value = user
          }
      }*/
}


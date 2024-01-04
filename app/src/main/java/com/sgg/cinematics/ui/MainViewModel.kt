package com.sgg.cinematics.ui


import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.sgg.cinematics.data.model.AuthUser
import com.sgg.cinematics.data.model.MovieModel
import com.sgg.cinematics.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
open class MainViewModel @Inject constructor(private val repository: MovieRepository) :
        ViewModel() {

    private var _topRatedMovies: Flow<List<MovieModel>> = repository.getTopRated()
    val topRatedMovies: Flow<List<MovieModel>>
        get() = _topRatedMovies

    private var _watchList: List<MovieModel> = repository.getWatchList()
    val watchList: List<MovieModel>
        get() = _watchList

    protected var _connectedUser: MutableStateFlow<FirebaseUser>? = null
    val connectedUser
        get() = _connectedUser?.asStateFlow()

    protected fun updateConnectedUser(user: AuthUser) {

    }
}

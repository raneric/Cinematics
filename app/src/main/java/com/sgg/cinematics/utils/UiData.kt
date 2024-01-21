package com.sgg.cinematics.utils

import com.google.firebase.auth.FirebaseUser
import com.sgg.cinematics.data.model.MovieModel
import kotlinx.coroutines.flow.Flow

sealed interface UiData {
    data class ListScreenData(val movieList: Flow<List<MovieModel>>) : UiData

    data class DetailScreenData(val movie: MovieModel) : UiData

    data class LoginUiState(var user: FirebaseUser?) : UiData
}

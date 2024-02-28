package com.sgg.cinematics.data.repository

import com.sgg.cinematics.utils.MovieListUiMode
import kotlinx.coroutines.flow.Flow

interface PreferenceRepository {

    val movieListUiModeFlow: Flow<MovieListUiMode>
    suspend fun updateUiState(movieListUiMode: MovieListUiMode)
}
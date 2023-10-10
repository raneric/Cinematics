package com.sgg.cinematics.data.repository

import com.sgg.cinematics.utils.MovieListUiMode

interface PreferenceRepository {
    suspend fun updateUiState(movieListUiMode: MovieListUiMode)
}
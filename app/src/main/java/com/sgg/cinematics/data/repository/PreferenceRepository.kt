package com.sgg.cinematics.data.repository

import com.sgg.cinematics.utils.UiState

interface PreferenceRepository {
    suspend fun updateUiState(uiState: UiState)
}
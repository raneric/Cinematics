package com.example.cinematics.data.repository

import com.example.cinematics.utils.UiState

interface PreferenceRepository {
    suspend fun updateUiState(uiState: UiState)
}
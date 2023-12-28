package com.sgg.cinematics.utils

sealed class UiState() {
    class Loading : UiState()
    class Success(val uiData: UiData) : UiState()
    class Error(val error: String) : UiState()
}

interface UiStateListener {
    fun updateUiState(uiState: UiState)
}
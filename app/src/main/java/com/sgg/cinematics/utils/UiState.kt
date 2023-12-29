package com.sgg.cinematics.utils

sealed interface UiState {
    class Loading : UiState
    class Success(val uiData: UiData) : UiState
    class Error(val error: String) : UiState
}

package com.sgg.cinematics.utils

sealed interface UiState {
    object Init : UiState
    object Loading : UiState
    object Success : UiState
    class Error(val error: String) : UiState
}

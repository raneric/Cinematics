package com.sgg.cinematics.utils

sealed class UiState() {
    class Loading : UiState()
    class Success : UiState()
    class Error(val error: String) : UiState()
}

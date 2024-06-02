package com.sgg.cinematics.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sgg.cinematics.ui.commonui.LoadingScreen
import com.sgg.cinematics.utils.UiState


/**
 * This composable wrap a screen and display component toward uiState :
 *  - [UiState.Success] : show component [componentOnSuccess] param
 *  - [UiState.Loading] : Show spinning progress
 *  - [UiState.Error] : show snackbar and error page with [componentOnError] param
 *
 * @param uiState : the current ui state
 * @param componentOnSuccess : component to display on success
 * @param componentOnError : component to display on error
 * @param modifier: A modifier with default value [Modifier]
 */
@Composable
fun ScreenWrapper(
    uiState: UiState,
    componentOnSuccess: @Composable () -> Unit
) {

    if (uiState is UiState.Loading) {
        LoadingScreen()
    }

    if (uiState is UiState.Success) {
        componentOnSuccess()
    }
}
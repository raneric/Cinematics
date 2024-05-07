package com.sgg.cinematics.ui

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Stable
import androidx.navigation.NavHostController
import com.sgg.cinematics.ui.components.NavItemVariant
import com.sgg.cinematics.utils.MovieListUiMode
import com.sgg.cinematics.utils.UiState

@Stable
class CinematicAppState(
        val navController: NavHostController,
        val movieListUiMode: MovieListUiMode,
        val windowWidthSizeClass: WindowWidthSizeClass,
        val isNavigationVisible: Boolean,
        val listUiState: UiState,
        val activeDestination: NavItemVariant,
) {
}
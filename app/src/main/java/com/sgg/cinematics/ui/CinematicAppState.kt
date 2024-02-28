package com.sgg.cinematics.ui

import androidx.navigation.NavHostController
import com.sgg.cinematics.data.model.MovieModel
import com.sgg.cinematics.utils.MovieListUiMode
import com.sgg.cinematics.utils.UiState


class CinematicAppState(
        val navController: NavHostController,
        val movieListUiMode: MovieListUiMode,
        val movies: List<MovieModel>,
        val listUiState: UiState,
) {
}
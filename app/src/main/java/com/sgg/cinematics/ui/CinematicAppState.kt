package com.sgg.cinematics.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavHostController
import com.sgg.cinematics.data.model.MovieModel
import com.sgg.cinematics.utils.MovieListUiMode
import com.sgg.cinematics.utils.UiState


@Composable
fun rememberSavableCinematicAppState(
        navController: NavHostController,
        movieListUiMode: MovieListUiMode,
        movies: List<MovieModel>,
        listUiState: UiState,
) = rememberSaveable(navController, movieListUiMode, movies, listUiState) {
    CinematicAppState(navController, movieListUiMode, movies, listUiState)
}


class CinematicAppState(
        val navController: NavHostController,
        val movieListUiMode: MovieListUiMode,
        val movies: List<MovieModel>,
        val listUiState: UiState,
) {
}
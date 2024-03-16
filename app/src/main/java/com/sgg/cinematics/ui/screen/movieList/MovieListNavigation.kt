package com.sgg.cinematics.ui.screen.movieList

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.sgg.cinematics.data.model.MovieModel
import com.sgg.cinematics.ui.commonui.ScreenWrapper
import com.sgg.cinematics.utils.Destination
import com.sgg.cinematics.utils.MovieListUiMode
import com.sgg.cinematics.utils.UiState

fun NavGraphBuilder.movieListScreen(
    navController: NavHostController,
    movies: List<MovieModel>,
    movieListUiMode: MovieListUiMode,
    listUiState: UiState,
    windowsWidthSizeClass: WindowWidthSizeClass,
) {
    composable(route = Destination.TrendingScreen.route) {
        ScreenWrapper(uiState = listUiState, componentOnSuccess = {
            MovieListScreen(movieListUiMode = movieListUiMode,
                            movieList = movies,
                            navController = navController,
                            windowsWidthSizeClass = windowsWidthSizeClass,
                            modifier = Modifier.semantics {
                                contentDescription = Destination.TrendingScreen.testTag
                            })
        }, componentOnError = { /*TODO*/ })
    }
}

fun NavGraphBuilder.topRatedListScreen(
    navController: NavHostController,
    movies: List<MovieModel>,
    movieListUiMode: MovieListUiMode,
    listUiState: UiState,
    windowsWidthSizeClass: WindowWidthSizeClass,
) {
    composable(route = Destination.TopRatedScreen.route) {
        ScreenWrapper(uiState = listUiState, componentOnSuccess = {
            MovieListScreen(movieListUiMode = movieListUiMode,
                            movieList = movies,
                            navController = navController,
                            windowsWidthSizeClass = windowsWidthSizeClass,
                            modifier = Modifier.semantics {
                                contentDescription = Destination.TrendingScreen.testTag
                            })
        }, componentOnError = { /*TODO*/ })
    }
}

fun NavGraphBuilder.watchListScreen(
    navController: NavHostController,
    movies: List<MovieModel>,
    movieListUiMode: MovieListUiMode,
    listUiState: UiState,
    windowsWidthSizeClass: WindowWidthSizeClass,
) {
    composable(route = Destination.WatchListScreen.route) {
        MovieListScreen(movieListUiMode = movieListUiMode,
                        movieList = movies,
                        navController = navController,
                        windowsWidthSizeClass = windowsWidthSizeClass,
                        modifier = Modifier.semantics {
                            contentDescription = Destination.WatchListScreen.testTag
                        })
    }
}

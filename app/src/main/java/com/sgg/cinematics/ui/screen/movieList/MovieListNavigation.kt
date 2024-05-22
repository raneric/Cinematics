package com.sgg.cinematics.ui.screen.movieList

import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sgg.cinematics.data.model.MovieModel
import com.sgg.cinematics.utils.Destination
import kotlinx.collections.immutable.ImmutableList

fun NavGraphBuilder.trendingListScreen(
        navController: NavHostController,
        windowsWidthSizeClass: WindowWidthSizeClass,
        movieList: ImmutableList<MovieModel>,
) {
    composable(route = Destination.TrendingScreen.route,
               enterTransition = { scaleIn() },
               exitTransition = { fadeOut() }) {
        MovieListScreen(
                navController = navController,
                windowsWidthSizeClass = windowsWidthSizeClass,
                movieList = movieList,
                modifier = Modifier.semantics {
                    contentDescription = Destination.TrendingScreen.testTag
                })
    }
}

fun NavGraphBuilder.watchListScreen(
        navController: NavHostController,
        windowsWidthSizeClass: WindowWidthSizeClass,
        movieList: ImmutableList<MovieModel>,
) {
    composable(route = Destination.WatchListScreen.route,
               exitTransition = { fadeOut() }) {
        MovieListScreen(
                navController = navController,
                windowsWidthSizeClass = windowsWidthSizeClass,
                movieList = movieList,
                modifier = Modifier.semantics {
                    contentDescription = Destination.WatchListScreen.testTag
                })
    }
}

fun NavHostController.navigateToMovieListScreen(navOption: NavOptions) {
    navigate(Destination.TrendingScreen.route, navOption)
}

fun NavHostController.navigateToWatchListScreen(navOption: NavOptions) {
    navigate(Destination.WatchListScreen.route, navOption)
}
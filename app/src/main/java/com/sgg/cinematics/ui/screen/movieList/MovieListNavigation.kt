package com.sgg.cinematics.ui.screen.movieList

import android.widget.Toast
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseUser
import com.sgg.cinematics.R
import com.sgg.cinematics.data.model.MovieModel
import com.sgg.cinematics.ui.CinematicsAppState
import com.sgg.cinematics.utils.Destination
import kotlinx.collections.immutable.ImmutableList

fun NavGraphBuilder.trendingListScreen(
    cinematicsAppState: CinematicsAppState,
    movieList: ImmutableList<MovieModel>,
    movieListViewModel: MovieListViewModel
) {
    composable(route = Destination.AllMoviesScreen.route,
               enterTransition = { scaleIn() },
               exitTransition = { fadeOut() }) {
        MovieListScreen(
            cinematicsAppState = cinematicsAppState,
            movieList = movieList,
            modifier = Modifier.semantics {
                contentDescription = Destination.AllMoviesScreen.testTag
            },
            movieListViewModel = movieListViewModel
        )
    }
}

fun NavGraphBuilder.watchListScreen(
    cinematicsAppState: CinematicsAppState,
    movieList: ImmutableList<MovieModel>,
    movieListViewModel: MovieListViewModel,
    currentUser: FirebaseUser?
) {

    composable(route = Destination.WatchListScreen.route,
               exitTransition = { fadeOut() }) {
        val context = LocalContext.current
        val errorMessage = stringResource(id = R.string.login_request)
        LaunchedEffect(key1 = currentUser) {
            if (currentUser == null) {
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT)
                    .show()
                cinematicsAppState.navigateTo(Destination.LoginScreen)
            }
        }

        MovieListScreen(
            cinematicsAppState = cinematicsAppState,
            movieList = movieList,
            modifier = Modifier.semantics {
                contentDescription = Destination.WatchListScreen.testTag
            },
            movieListViewModel = movieListViewModel
        )

    }
}

fun NavHostController.navigateToMovieListScreen(navOption: NavOptions) {
    navigate(Destination.AllMoviesScreen.route, navOption)
}

fun NavHostController.navigateToWatchListScreen(navOption: NavOptions) {
    navigate(Destination.WatchListScreen.route, navOption)
}
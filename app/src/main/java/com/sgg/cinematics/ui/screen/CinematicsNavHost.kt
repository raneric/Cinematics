package com.sgg.cinematics.ui.screen

import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.google.firebase.auth.FirebaseUser
import com.sgg.cinematics.data.model.MovieModel
import com.sgg.cinematics.ui.screen.account.createAccountScreen
import com.sgg.cinematics.ui.screen.account.userProfileScreen
import com.sgg.cinematics.ui.screen.details.detailsScreen
import com.sgg.cinematics.ui.screen.login.loginScreen
import com.sgg.cinematics.ui.screen.movieList.trendingListScreen
import com.sgg.cinematics.ui.screen.movieList.watchListScreen
import com.sgg.cinematics.utils.Destination
import com.sgg.cinematics.utils.MovieListUiMode
import com.sgg.cinematics.utils.UiState
import kotlinx.collections.immutable.ImmutableList

// TODO: Refactoring for this composable function
@Composable
fun CinematicsNavHost(
    navController: NavHostController,
    movieListUiMode: MovieListUiMode,
    movies: ImmutableList<MovieModel>,
    listUiState: UiState,
    connectedUser: FirebaseUser?,
    windowsWidthSizeClass: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) {

    NavHost(
            navController = navController,
            startDestination = Destination.TrendingScreen.route,
            modifier = modifier,
            enterTransition = { slideInVertically() },
            exitTransition = { slideOutVertically() }
    ) {

        trendingListScreen(
                movieListUiMode = movieListUiMode,
                movies = movies,
                listUiState = listUiState,
                navController = navController,
                windowsWidthSizeClass = windowsWidthSizeClass,
        )

        watchListScreen(
                movieListUiMode = movieListUiMode,
                movies = movies,
                listUiState = listUiState,
                navController = navController,
                windowsWidthSizeClass = windowsWidthSizeClass,
        )

        detailsScreen(navController)

        userProfileScreen(navController = navController, connectedUser = connectedUser)

        loginScreen(navController)

        createAccountScreen(navController)
    }
}

const val MOVIE_ID_ARGS = "movieId"
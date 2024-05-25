package com.sgg.cinematics.ui.screen

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.google.firebase.auth.FirebaseUser
import com.sgg.cinematics.data.model.MovieModel
import com.sgg.cinematics.ui.CinematicsAppState
import com.sgg.cinematics.ui.screen.account.createAccountScreen
import com.sgg.cinematics.ui.screen.details.detailsScreen
import com.sgg.cinematics.ui.screen.login.loginScreen
import com.sgg.cinematics.ui.screen.movieList.MovieListViewModel
import com.sgg.cinematics.ui.screen.movieList.trendingListScreen
import com.sgg.cinematics.ui.screen.movieList.watchListScreen
import com.sgg.cinematics.ui.screen.profile.userProfileScreen
import com.sgg.cinematics.utils.Destination
import kotlinx.collections.immutable.ImmutableList

@Composable
fun CinematicsNavHost(
        connectedUser: FirebaseUser?,
        cinematicsAppState: CinematicsAppState,
        movieList: ImmutableList<MovieModel>,
        modifier: Modifier = Modifier,
        movieListViewModel: MovieListViewModel
) {

    NavHost(
            navController = cinematicsAppState.navController,
            startDestination = Destination.AllMoviesScreen.route,
            modifier = modifier,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
    ) {

        trendingListScreen(
                cinematicsAppState = cinematicsAppState,
                movieList = movieList,
                movieListViewModel = movieListViewModel
        )

        watchListScreen(
                cinematicsAppState = cinematicsAppState,
                movieList = movieList,
                movieListViewModel = movieListViewModel
        )

        detailsScreen(navController = cinematicsAppState.navController,
                      connectedUser = connectedUser)

        userProfileScreen(navController = cinematicsAppState.navController,
                          connectedUser = connectedUser)

        loginScreen(cinematicsAppState.navController)

        createAccountScreen(cinematicsAppState.navController)
    }
}

const val MOVIE_ID_ARGS = "movieId"
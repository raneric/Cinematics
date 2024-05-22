package com.sgg.cinematics.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sgg.cinematics.ui.CinematicsAppState
import com.sgg.cinematics.ui.MainViewModel
import com.sgg.cinematics.ui.components.BottomNavScreen
import com.sgg.cinematics.ui.components.CinematicsNavigationRail
import com.sgg.cinematics.ui.screen.movieList.MovieListViewModel
import kotlinx.collections.immutable.toImmutableList

@Composable
fun CinematicsAppScreen(
        modifier: Modifier = Modifier,
        cinematicsAppState: CinematicsAppState
) {
    val mainViewModel = hiltViewModel<MainViewModel>()
    val movieListViewModel = hiltViewModel<MovieListViewModel>()

    val connectedUser by mainViewModel.connectedUser.collectAsStateWithLifecycle(initialValue = null)
    val snackbarHostState = remember { SnackbarHostState() }
    val movieList by movieListViewModel.movies.collectAsStateWithLifecycle()

    cinematicsAppState.navController.addOnDestinationChangedListener { _, destination, _ ->
        destination.route?.let {
            movieListViewModel.updateMovieList(it)
        }
    }

    Scaffold(
            modifier = modifier,
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
            bottomBar = {
                AnimatedVisibility(visible = cinematicsAppState.shouldShowBottomNav,
                                   enter = slideInVertically(initialOffsetY = { -40 })
                ) {
                    BottomNavScreen(
                            activeNavItem = cinematicsAppState.activeDestination,
                            onDestinationChanged = cinematicsAppState::navigateTo,
                            navController = cinematicsAppState.navController)
                }
            }) { paddingValue ->

        Row {
            if (cinematicsAppState.shouldShowNavRail) {
                CinematicsNavigationRail(activeNavItem = cinematicsAppState.activeDestination,
                                         onDestinationChanged = cinematicsAppState::navigateTo)
            }
            CinematicsNavHost(
                    cinematicsAppState = cinematicsAppState,
                    connectedUser = connectedUser,
                    modifier = Modifier.padding(paddingValue),
                    movieList = movieList.toImmutableList()
            )
        }
    }
}

package com.sgg.cinematics.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseUser
import com.sgg.cinematics.data.model.MovieModel
import com.sgg.cinematics.ui.MainViewModel
import com.sgg.cinematics.ui.components.BottomNavScreen
import com.sgg.cinematics.ui.components.CinematicsNavigationRail
import com.sgg.cinematics.ui.components.NavItemVariant
import com.sgg.cinematics.ui.screen.movieList.MovieListViewModel
import com.sgg.cinematics.utils.MovieListUiMode
import com.sgg.cinematics.utils.UiState
import com.sgg.cinematics.utils.activeNavItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun CinematicsAppScreen(
        windowsWidthSizeClass: WindowWidthSizeClass
) {
    val mainViewModel = hiltViewModel<MainViewModel>()
    val movieListViewModel = hiltViewModel<MovieListViewModel>()

    val navController = rememberNavController()

    val connectedUser by mainViewModel.connectedUser.collectAsStateWithLifecycle(initialValue = null)

    val movies by movieListViewModel.movies.collectAsStateWithLifecycle()

    val listUiState by movieListViewModel.listUiState.collectAsStateWithLifecycle()

    val uiListMode by movieListViewModel.uiListMode.collectAsStateWithLifecycle(initialValue = MovieListUiMode.ListView)

    val activeDestination = if (navController.currentDestination != null) {
        navController.currentDestination!!.activeNavItem()
    } else {
        NavItemVariant.Trending
    }

    navController.addOnDestinationChangedListener { _, navDestination, _ ->
        navDestination.route?.let { route ->
            movieListViewModel.updateMovieList(route)
        }
    }

    if (windowsWidthSizeClass == WindowWidthSizeClass.Compact) {
        CinematicsAppCompact(
                navController = navController,
                uiListMode = uiListMode,
                movies = movies.toImmutableList(),
                connectedUser = connectedUser,
                listUiState = listUiState,
                windowsWidthSizeClass = windowsWidthSizeClass)
    } else {
        CinematicsAppMedium(
                navController = navController,
                activeDestination = activeDestination,
                uiListMode = uiListMode,
                movies = movies.toImmutableList(),
                connectedUser = connectedUser,
                listUiState = listUiState,
                windowsWidthSizeClass = windowsWidthSizeClass,
        )
    }
}

/**
 * A composable that display  app in a [Scaffold] when [WindowWidthSizeClass] is Compact
 *
 * @param isBottomNavVisible : a flag for bottomNav and fab button visibility - Hidden in details screen
 * @param activeDestination : [NavItemVariant] that is the active Destination
 * @param navController : [NavHostController]
 * @param uiListMode : display list mode [MovieListUiMode] List or Carousel
 * @param viewModel
 * @param modifier
 */
@Composable
fun CinematicsAppCompact(
        modifier: Modifier = Modifier,
        navController: NavHostController,
        uiListMode: MovieListUiMode,
        movies: ImmutableList<MovieModel>,
        listUiState: UiState,
        connectedUser: FirebaseUser?,
        windowsWidthSizeClass: WindowWidthSizeClass
) {

    var isBottomNavVisible by rememberSaveable {
        mutableStateOf(true)
    }

    Scaffold(
            modifier = modifier,
            bottomBar = {
                AnimatedVisibility(visible = isBottomNavVisible,
                                   enter = slideInVertically(initialOffsetY = { -40 })
                ) {
                    BottomNavScreen(onDestinationChange = {
                        isBottomNavVisible = it
                    }, navController = navController)
                }
            }) { paddingValue ->
        CinematicsNavHost(
                navController = navController,
                movieListUiMode = uiListMode,
                movies = movies,
                listUiState = listUiState,
                connectedUser = connectedUser,
                windowsWidthSizeClass = windowsWidthSizeClass,
                modifier = Modifier.padding(paddingValue)
        )
    }
}

/**
 * A composable that display  app in a [Row] with a [CinematicsNavigationRail] when
 * [WindowWidthSizeClass] is Medium or expanded
 *
 * @param activeDestination : [NavItemVariant] that is the active Destination
 * @param navController : [NavHostController]
 * @param uiListMode : display list mode [MovieListUiMode] List or Carousel
 * @param viewModel
 * @param modifier
 */
@Composable
fun CinematicsAppMedium(
        modifier: Modifier = Modifier,
        navController: NavHostController,
        activeDestination: NavItemVariant,
        uiListMode: MovieListUiMode,
        movies: ImmutableList<MovieModel>,
        listUiState: UiState,
        connectedUser: FirebaseUser?,
        windowsWidthSizeClass: WindowWidthSizeClass
) {
    Row(modifier = modifier) {
        CinematicsNavigationRail(activeNavItem = activeDestination) {
            navController.navigate(it.route)
        }
        CinematicsNavHost(
                navController = navController,
                movieListUiMode = uiListMode,
                movies = movies.toImmutableList(),
                listUiState = listUiState,
                connectedUser = connectedUser,
                windowsWidthSizeClass = windowsWidthSizeClass
        )
    }
}

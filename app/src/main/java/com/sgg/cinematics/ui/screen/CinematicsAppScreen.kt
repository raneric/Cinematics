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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseUser
import com.sgg.cinematics.data.model.MovieModel
import com.sgg.cinematics.ui.MainViewModel
import com.sgg.cinematics.ui.commonui.MovieDisplaySwitchFab
import com.sgg.cinematics.ui.components.BottomNavScreen
import com.sgg.cinematics.ui.components.CinematicsNavigationRail
import com.sgg.cinematics.ui.components.NavItemVariant
import com.sgg.cinematics.ui.screen.movieList.MovieListViewModel
import com.sgg.cinematics.utils.Destination
import com.sgg.cinematics.utils.MovieListUiMode
import com.sgg.cinematics.utils.UiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun CinematicsAppScreen(
    windowsWidthSizeClass: WindowWidthSizeClass
) {
    val mainViewModel = hiltViewModel<MainViewModel>()
    val movieListViewModel = hiltViewModel<MovieListViewModel>()

    val navController = rememberNavController()

    val connectedUser = mainViewModel.connectedUser.collectAsStateWithLifecycle(initialValue = null)

    var isBottomNavVisible: Boolean by rememberSaveable {
        mutableStateOf(true)
    }

    var isFabViewSwitchVisible: Boolean by rememberSaveable {
        mutableStateOf(true)
    }

    var activeDestination: NavItemVariant by remember {
        mutableStateOf(NavItemVariant.Trending)
    }

    val movies = movieListViewModel.movies.collectAsStateWithLifecycle()

    val listUiState = movieListViewModel.listUiState.collectAsStateWithLifecycle()

    val uiListMode = movieListViewModel.uiListMode.collectAsStateWithLifecycle(initialValue = MovieListUiMode.ListView)

    navController.addOnDestinationChangedListener { _, navDestination, _ ->
        activeDestination = navDestination.activeNavItem()
        isBottomNavVisible = navDestination.isInBottomNavDestination()
        isFabViewSwitchVisible = navDestination.isIntListDestination()
        navDestination.route?.let { route ->
            movieListViewModel.updateMovieList(route)
        }
    }

    if (windowsWidthSizeClass == WindowWidthSizeClass.Compact) {
        CinematicsAppCompact(
                isBottomNavVisible = isBottomNavVisible,
                isFabViewSwitchVisible = isFabViewSwitchVisible,
                activeDestination = activeDestination,
                navController = navController,
                uiListMode = uiListMode.value,
                movies = movies.value.toImmutableList(),
                connectedUser = connectedUser.value,
                listUiState = listUiState.value,
                windowsWidthSizeClass = windowsWidthSizeClass,
                viewModel = movieListViewModel
        )
    } else {
        CinematicsAppMedium(
                navController = navController,
                activeDestination = activeDestination,
                uiListMode = uiListMode.value,
                movies = movies.value.toImmutableList(),
                connectedUser = connectedUser.value,
                listUiState = listUiState.value,
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
    isBottomNavVisible: Boolean,
    isFabViewSwitchVisible: Boolean,
    activeDestination: NavItemVariant,
    navController: NavHostController,
    uiListMode: MovieListUiMode,
    viewModel: MovieListViewModel,
    movies: ImmutableList<MovieModel>,
    listUiState: UiState,
    connectedUser: FirebaseUser?,
    windowsWidthSizeClass: WindowWidthSizeClass
) {

    Scaffold(
            modifier = modifier,
            bottomBar = {
                AnimatedVisibility(visible = isBottomNavVisible,
                                   enter = slideInVertically(initialOffsetY = { -40 })
                ) {
                    BottomNavScreen(activeNavItem = activeDestination,
                                    onItemClicked = { navController.navigate(it.route) })
                }
            }, floatingActionButton = {
        if (isFabViewSwitchVisible) {
            MovieDisplaySwitchFab(uiListMode.fabIcon) {
                val nextMovieListUiMode = if (uiListMode is MovieListUiMode.ListView) MovieListUiMode.CarouselView else MovieListUiMode.ListView
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.switchListViewMode(nextMovieListUiMode)
                }
            }
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

/**
 * Private extension function for [NavDestination] that will return a [NavItemVariant]
 * depending on the selected destination
 */
private fun NavDestination.activeNavItem(): NavItemVariant {
    return when (this.route) {
        Destination.TopRatedScreen.route    -> NavItemVariant.TopRated
        Destination.WatchListScreen.route   -> NavItemVariant.WatchList
        Destination.UserProfileScreen.route -> NavItemVariant.UserProfile
        else                                -> NavItemVariant.Trending
    }
}

private fun NavDestination.isInBottomNavDestination(): Boolean {
    return this.route == Destination.TrendingScreen.route ||
           this.route == Destination.TopRatedScreen.route ||
           this.route == Destination.WatchListScreen.route ||
           this.route == Destination.UserProfileScreen.route
}

private fun NavDestination.isIntListDestination(): Boolean {
    return this.route == Destination.TrendingScreen.route ||
           this.route == Destination.TopRatedScreen.route ||
           this.route == Destination.WatchListScreen.route
}

private fun NavHostController.navigateIfNotMovieList(navItem: NavItemVariant) {
    if (!navItem.isInMovieList()) {
        navigate(navItem.route)
    }
}

private fun NavItemVariant.isInMovieList(): Boolean {
    return this == NavItemVariant.Trending ||
           this == NavItemVariant.TopRated ||
           this == NavItemVariant.WatchList
}
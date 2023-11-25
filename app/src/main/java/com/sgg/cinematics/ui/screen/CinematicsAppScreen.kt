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
import com.sgg.cinematics.ui.MainViewModel
import com.sgg.cinematics.ui.commonui.MovieDisplaySwitchFab
import com.sgg.cinematics.ui.components.BottomNavScreen
import com.sgg.cinematics.ui.components.CinematicsNavigationRail
import com.sgg.cinematics.ui.components.NavItemVariant
import com.sgg.cinematics.ui.screen.movieList.MovieListViewModel
import com.sgg.cinematics.utils.Destination
import com.sgg.cinematics.utils.MovieListUiMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun CinematicsAppScreen(windowsWidthSizeClass: WindowWidthSizeClass) {

    val movieListViewModel = hiltViewModel<MovieListViewModel>()

    val navController = rememberNavController()

    var isBottomNavVisible: Boolean by rememberSaveable {
        mutableStateOf(true)
    }

    var isFabViewSwitchVisible: Boolean by rememberSaveable {
        mutableStateOf(true)
    }

    var activeDestination: NavItemVariant by remember { mutableStateOf(NavItemVariant.Trending) }

    val uiListMode = movieListViewModel.uiListState.collectAsStateWithLifecycle(initialValue = MovieListUiMode.ListView)

    navController.addOnDestinationChangedListener { _, navDestination, _ ->
        activeDestination = navDestination.activeNavItem()
        isBottomNavVisible = navDestination.route != Destination.DetailScreen.route
        isFabViewSwitchVisible = navDestination.route != Destination.DetailScreen.route && navDestination.route != Destination.UserProfileScreen.route
    }

    if (windowsWidthSizeClass == WindowWidthSizeClass.Compact) {
        CinematicsAppCompact(isBottomNavVisible = isBottomNavVisible,
                             isFabViewSwitchVisible = isFabViewSwitchVisible,
                             activeDestination = activeDestination,
                             navController = navController,
                             uiListMode = uiListMode.value,
                             windowsWidthSizeClass = windowsWidthSizeClass,
                             viewModel = movieListViewModel)
    } else {
        CinematicsAppMedium(navController = navController,
                            activeDestination = activeDestination,
                            uiListMode = uiListMode.value,
                            viewModel = movieListViewModel,
                            windowsWidthSizeClass = windowsWidthSizeClass)
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
fun CinematicsAppCompact(isBottomNavVisible: Boolean,
                         isFabViewSwitchVisible: Boolean,
                         activeDestination: NavItemVariant,
                         navController: NavHostController,
                         uiListMode: MovieListUiMode,
                         viewModel: MovieListViewModel,
                         windowsWidthSizeClass: WindowWidthSizeClass,
                         modifier: Modifier = Modifier) {
    Scaffold(bottomBar = {
        AnimatedVisibility(visible = isBottomNavVisible,
                           enter = slideInVertically(initialOffsetY = { -40 })) {
            BottomNavScreen(activeDestination = activeDestination) { destinationRoute ->
                navController.navigate(route = destinationRoute)
            }
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
        CinematicsNavHost(navController = navController,
                          movieListViewModel = viewModel,
                          movieListUiMode = uiListMode,
                          windowsWidthSizeClass = windowsWidthSizeClass,
                          modifier = Modifier.padding(paddingValue))
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
fun CinematicsAppMedium(navController: NavHostController,
                        activeDestination: NavItemVariant,
                        uiListMode: MovieListUiMode,
                        viewModel: MainViewModel,
                        windowsWidthSizeClass: WindowWidthSizeClass,
                        modifier: Modifier = Modifier) {
    Row {
        CinematicsNavigationRail(activeDestination = activeDestination) {
            navController.navigate(it)
        }
        CinematicsNavHost(navController = navController,
                          movieListViewModel = viewModel,
                          movieListUiMode = uiListMode,
                          windowsWidthSizeClass = windowsWidthSizeClass)
    }
}

/**
 * Private extension function for [NavDestination] that will return a [NavItemVariant]
 * depending on the selected destination
 */
private fun NavDestination.activeNavItem(): NavItemVariant {
    return when (this.route) {
        Destination.TopRatedScreen.route -> NavItemVariant.TopRated
        Destination.WatchListScreen.route -> NavItemVariant.WatchList
        Destination.UserProfileScreen.route -> NavItemVariant.UserProfile
        else -> NavItemVariant.Trending
    }
}
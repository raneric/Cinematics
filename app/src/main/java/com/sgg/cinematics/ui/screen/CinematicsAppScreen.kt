package com.sgg.cinematics.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.compose.rememberNavController
import com.sgg.cinematics.ui.MainViewModel
import com.sgg.cinematics.ui.commonui.MovieDisplaySwitchFab
import com.sgg.cinematics.ui.components.NavItemVariant
import com.sgg.cinematics.ui.components.BottomNavScreen
import com.sgg.cinematics.utils.Destination
import com.sgg.cinematics.utils.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun CinematicsAppScreen(viewModel: MainViewModel) {

    val navController = rememberNavController()

    var isNotDetailScreen: Boolean by rememberSaveable { mutableStateOf(true) }

    var activeDestination: NavItemVariant by remember { mutableStateOf(NavItemVariant.Trending) }

    val uiListState = viewModel.uiListState.collectAsStateWithLifecycle(initialValue = UiState.ListView)

    navController.addOnDestinationChangedListener { _, navDestination, _ ->
        activeDestination = navDestination.activeBottomNavItem()
        isNotDetailScreen = navDestination.route != Destination.DetailScreen.route
    }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(visible = isNotDetailScreen,
                               enter = slideInVertically(initialOffsetY = { -40 })) {
                BottomNavScreen(activeDestination = activeDestination) {
                    navController.navigate(route = it)
                }
            }
        },
        floatingActionButton = {
            if (isNotDetailScreen) {
                MovieDisplaySwitchFab(uiListState.value.fabIcon) {
                    val nextUiState = if (uiListState.value is UiState.ListView) UiState.CarouselView else UiState.ListView
                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel.switchListViewMode(nextUiState)
                    }
                }
            }
        }
    ) { paddingValue ->
        CinematicsNavHost(navController = navController,
                          viewModel = viewModel,
                          uiState = uiListState.value,
                          modifier = Modifier.padding(paddingValue))
    }
}

@Composable
fun CinematicsAppCompact(
        modifier: Modifier = Modifier
) {

}

private fun NavDestination.activeBottomNavItem(): NavItemVariant {
    return when (this.route) {
        Destination.TopRatedScreen.route -> NavItemVariant.TopRated
        Destination.WatchListScreen.route -> NavItemVariant.WatchList
        Destination.UserProfileScreen.route -> NavItemVariant.UserProfile
        else -> NavItemVariant.Trending
    }
}
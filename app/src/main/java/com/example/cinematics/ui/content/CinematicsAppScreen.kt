package com.example.cinematics.ui.content

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
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
import com.example.cinematics.ui.MainViewModel
import com.example.cinematics.ui.commonui.MovieDisplaySwitchFab
import com.example.cinematics.ui.components.BottomNavItemVariant
import com.example.cinematics.ui.components.BottomNavScreen
import com.example.cinematics.utils.Destination
import com.example.cinematics.utils.UiState

@Composable
fun CinematicsAppScreen(viewModel: MainViewModel) {

    val navController = rememberNavController()
    var isNotDetailScreen: Boolean by rememberSaveable { mutableStateOf(true) }
    var activeDestination: BottomNavItemVariant by remember { mutableStateOf(BottomNavItemVariant.Trending) }
    val uiState = viewModel.uiListState.collectAsStateWithLifecycle()
    navController.addOnDestinationChangedListener { _, navDestination, _ ->
        activeDestination = navDestination.activeBottomNavItem()
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
            MovieDisplaySwitchFab(uiState.value.fabIcon) {
                viewModel.switchListView()
            }
        }
    ) { paddingValue ->
        CinematicsNavHost(navController = navController,
                          viewModel = viewModel,
                          uiState = uiState.value,
                          modifier = Modifier.padding(paddingValue)) {
            isNotDetailScreen = it
        }
    }
}

private fun NavDestination.activeBottomNavItem(): BottomNavItemVariant {
    return when (this.route) {
        Destination.TopRatedScreen.route -> BottomNavItemVariant.TopRated
        Destination.WatchListScreen.route -> BottomNavItemVariant.WatchList
        else -> BottomNavItemVariant.Trending
    }
}
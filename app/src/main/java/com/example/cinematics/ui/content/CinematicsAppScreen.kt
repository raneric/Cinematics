package com.example.cinematics.ui.content

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
import androidx.navigation.NavDestination
import androidx.navigation.compose.rememberNavController
import com.example.cinematics.ui.MainViewModel
import com.example.cinematics.ui.components.BottomNavItemVariant
import com.example.cinematics.ui.components.BottomNavScreen
import com.example.cinematics.utils.CinematicsDestination

@Composable
fun CinematicsAppScreen(viewModel: MainViewModel) {

    val navController = rememberNavController()
    var showBottomNav: Boolean by rememberSaveable { mutableStateOf(true) }
    var activeDestination: BottomNavItemVariant by remember { mutableStateOf(BottomNavItemVariant.Trending) }

    navController.addOnDestinationChangedListener { _, navDestination, _ ->
        activeDestination = navDestination.activeBottomNavItem()
    }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(visible = showBottomNav,
                               enter = slideInVertically(initialOffsetY = { -40 })) {
                BottomNavScreen(activeDestination = activeDestination) {
                    navController.navigate(route = it)
                }
            }
        }
    ) { paddingValue ->
        CinematicsNavHost(navController = navController,
                          viewModel = viewModel,
                          modifier = Modifier.padding(paddingValue)) {
            showBottomNav = !it
        }
    }
}

private fun NavDestination.activeBottomNavItem(): BottomNavItemVariant {
    return when (this.route) {
        CinematicsDestination.TOP_RATED.route -> BottomNavItemVariant.TopRated
        CinematicsDestination.WATCH_LIST.route -> BottomNavItemVariant.WatchList
        else -> BottomNavItemVariant.Trending
    }
}
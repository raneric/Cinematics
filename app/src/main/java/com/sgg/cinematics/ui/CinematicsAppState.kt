package com.sgg.cinematics.ui

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.sgg.cinematics.ui.components.NavItemVariant
import com.sgg.cinematics.ui.screen.movieList.navigateToMovieListScreen
import com.sgg.cinematics.ui.screen.movieList.navigateToWatchListScreen
import com.sgg.cinematics.ui.screen.profile.navigateToUserProfileScreen
import com.sgg.cinematics.utils.Destination
import com.sgg.cinematics.utils.activeNavItem
import com.sgg.cinematics.utils.isInBottomNavDestination

@Composable
fun rememberCinematicsAppState(
        navController: NavHostController = rememberNavController(),
        windowWidthSizeClass: WindowWidthSizeClass
) =
        remember(navController, windowWidthSizeClass) {
            CinematicsAppState(navController,
                               windowWidthSizeClass)
        }

@Stable
class CinematicsAppState(
        val navController: NavHostController,
        val windowWidthSizeClass: WindowWidthSizeClass,
) {
    val activeDestination: NavItemVariant
        @Composable get() {
            return navController.currentDestination?.activeNavItem()
                   ?: NavItemVariant.Trending
        }

    val shouldShowBottomNav: Boolean
        @Composable get() = windowWidthSizeClass == WindowWidthSizeClass.Compact && currentDestinationIsInNavDestination
    val shouldShowNavRail: Boolean
        @Composable get() = windowWidthSizeClass != WindowWidthSizeClass.Compact && currentDestinationIsInNavDestination

    private val currentDestinationIsInNavDestination: Boolean
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination?.isInBottomNavDestination()
                            ?: true

    fun navigateTo(destination: Destination) {
        val navOption = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (destination) {
            Destination.TrendingScreen    -> navController.navigateToMovieListScreen(navOption)
            Destination.WatchListScreen   -> navController.navigateToWatchListScreen(navOption)
            Destination.UserProfileScreen -> navController.navigateToUserProfileScreen(navOption)
            Destination.CreateAccount     -> TODO()
            Destination.DetailScreen      -> TODO()
            Destination.LoginScreen       -> TODO()
        }
    }

}
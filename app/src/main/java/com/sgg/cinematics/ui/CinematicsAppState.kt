package com.sgg.cinematics.ui

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.sgg.cinematics.ui.components.NavItemVariant
import com.sgg.cinematics.ui.screen.account.navigateToCreateAccount
import com.sgg.cinematics.ui.screen.login.navigateToLoginScreen
import com.sgg.cinematics.ui.screen.movieList.navigateToMovieListScreen
import com.sgg.cinematics.ui.screen.movieList.navigateToWatchListScreen
import com.sgg.cinematics.ui.screen.profile.navigateToUserProfileScreen
import com.sgg.cinematics.utils.Destination
import com.sgg.cinematics.utils.activeNavItem
import com.sgg.cinematics.utils.isInBottomNavDestination

@Composable
fun rememberCinematicsAppState(
    windowWidthSizeClass: WindowWidthSizeClass,
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
    navController: NavHostController = rememberNavController()
) =
    remember(navController, windowWidthSizeClass) {
        CinematicsAppState(
            navController = navController,
            snackbarHostState = snackbarHostState,
            windowWidthSizeClass = windowWidthSizeClass
        )
    }

@Stable
class CinematicsAppState(
    val navController: NavHostController,
    val windowWidthSizeClass: WindowWidthSizeClass,
    val snackbarHostState: SnackbarHostState,
) {
    val activeNavItem: NavItemVariant
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
            launchSingleTop = true
        }
        when (destination) {
            Destination.AllMoviesScreen   -> navController.navigateToMovieListScreen(navOption)
            Destination.WatchListScreen   -> navController.navigateToWatchListScreen(navOption)
            Destination.UserProfileScreen -> navController.navigateToUserProfileScreen(navOption)
            Destination.CreateAccount     -> navController.navigateToCreateAccount(navOption)
            Destination.DetailScreen      -> TODO()
            Destination.LoginScreen       -> navController.navigateToLoginScreen()
        }
    }

    suspend fun displaySnackBar(
        message: String,
        actionLabel: String? = null,
        action: () -> Unit = {}
    ) {
        val snackBarResult =
            snackbarHostState.showSnackbar(
                message = message,
                actionLabel = actionLabel,
                duration = if (actionLabel == null) SnackbarDuration.Short else SnackbarDuration.Long
            )
        when (snackBarResult) {
            SnackbarResult.ActionPerformed -> action()

            SnackbarResult.Dismissed       -> snackbarHostState.currentSnackbarData?.dismiss()
        }
    }
}
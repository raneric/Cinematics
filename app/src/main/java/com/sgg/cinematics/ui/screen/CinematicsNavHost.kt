package com.sgg.cinematics.ui.screen

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.google.firebase.auth.FirebaseUser
import com.sgg.cinematics.ui.CinematicsAppState
import com.sgg.cinematics.ui.screen.account.createAccountScreen
import com.sgg.cinematics.ui.screen.details.detailsScreen
import com.sgg.cinematics.ui.screen.login.loginScreen
import com.sgg.cinematics.ui.screen.movieList.trendingListScreen
import com.sgg.cinematics.ui.screen.movieList.watchListScreen
import com.sgg.cinematics.ui.screen.profile.userProfileScreen
import com.sgg.cinematics.utils.Destination

@Composable
fun CinematicsNavHost(
        connectedUser: FirebaseUser?,
        cinematicsAppState: CinematicsAppState,
        modifier: Modifier = Modifier
) {

    NavHost(
            navController = cinematicsAppState.navController,
            startDestination = Destination.TrendingScreen.route,
            modifier = modifier,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
    ) {

        trendingListScreen(
                navController = cinematicsAppState.navController,
                windowsWidthSizeClass = cinematicsAppState.windowWidthSizeClass,
        )

        watchListScreen(
                navController = cinematicsAppState.navController,
                windowsWidthSizeClass = cinematicsAppState.windowWidthSizeClass,
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
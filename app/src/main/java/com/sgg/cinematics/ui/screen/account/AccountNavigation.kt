package com.sgg.cinematics.ui.screen.account

import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.sgg.cinematics.utils.Destination


fun NavGraphBuilder.createAccountScreen(navController: NavHostController) {
    composable(route = Destination.CreateAccount.route,
               enterTransition = { scaleIn() },
               exitTransition = { scaleOut() }
    ) {
        CreateAccountScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                navigateToUserProfile = {
                    navController.navigate(Destination.UserProfileScreen.route)
                }
        )
    }
}
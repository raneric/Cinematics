package com.sgg.cinematics.ui.screen.account

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.sgg.cinematics.utils.Destination


fun NavGraphBuilder.createAccountScreen(navController: NavHostController) {
    composable(route = Destination.CreateAccount.route,
               enterTransition = null,
               exitTransition = null
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
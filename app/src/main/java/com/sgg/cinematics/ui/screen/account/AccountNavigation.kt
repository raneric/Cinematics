package com.sgg.cinematics.ui.screen.account

import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseUser
import com.sgg.cinematics.data.userModelLists
import com.sgg.cinematics.ui.screen.login.LoginViewModel
import com.sgg.cinematics.utils.Destination

fun NavGraphBuilder.userProfileScreen(
    navController: NavHostController,
    connectedUser: FirebaseUser?
) {
    composable(route = Destination.UserProfileScreen.route,
               enterTransition = { scaleIn() },
               exitTransition = { fadeOut() }
    ) {

        val loginViewModel = hiltViewModel<LoginViewModel>()

        if (connectedUser == null) {
            navController.navigate(Destination.LoginScreen.route)
        } else {
            UserProfileScreen(user = userModelLists[0]) {
                loginViewModel.logout()
            }
        }
    }
}

fun NavGraphBuilder.createAccountScreen(navController: NavHostController) {
    composable(route = Destination.CreateAccount.route,
               enterTransition = { scaleIn() },
               exitTransition = { scaleOut() }
    ) {
        CreateAccountScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }, onCreateAccountClick = {

        })
    }
}
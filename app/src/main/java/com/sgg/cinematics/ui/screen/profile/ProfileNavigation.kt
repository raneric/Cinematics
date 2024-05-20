package com.sgg.cinematics.ui.screen.profile

import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseUser
import com.sgg.cinematics.utils.Destination

fun NavGraphBuilder.userProfileScreen(
        navController: NavHostController,
        connectedUser: FirebaseUser?
) {
    composable(route = Destination.UserProfileScreen.route,
               enterTransition = { scaleIn() },
               exitTransition = { fadeOut() }
    ) {
        val viewModel = hiltViewModel<UserProfileViewModel>()
        val user = viewModel.user

        if (connectedUser == null) {
            navController.navigate(Destination.LoginScreen.route) {
                popUpTo(Destination.UserProfileScreen.route) {
                    inclusive = true
                }
            }
        } else {
            viewModel.refreshUserInfo(connectedUser.uid!!)
            UserProfileScreen(user = user,
                              logout = {
                                  viewModel.logout()
                              },
                              onEditClicked = { })
        }
    }
}

fun NavHostController.navigateToUserProfileScreen(navOption: NavOptions) {
    navigate(Destination.UserProfileScreen.route, navOption)
}
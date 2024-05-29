package com.sgg.cinematics.ui.screen.profile

import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseUser
import com.sgg.cinematics.ui.commonui.ScreenWrapper
import com.sgg.cinematics.utils.Destination

fun NavGraphBuilder.userProfileScreen(
        navController: NavHostController,
        currentUser: FirebaseUser?
) {

    composable(route = Destination.UserProfileScreen.route,
               enterTransition = { scaleIn() },
               exitTransition = { fadeOut() }
    ) {
        val viewModel = hiltViewModel<UserProfileViewModel>()
        val user = viewModel.user.collectAsStateWithLifecycle()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()
        LaunchedEffect(key1 = currentUser) {
            when {
                currentUser == null -> {
                    navController.navigate(Destination.LoginScreen.route) {
                        popUpTo(Destination.UserProfileScreen.route) {
                            inclusive = true
                        }
                    }
                }

                else                -> {
                    viewModel.refreshUserInfo(currentUser.uid!!)
                }
            }
        }
        ScreenWrapper(uiState = uiState.value,
                      componentOnSuccess = {
                          UserProfileScreen(user = user.value,
                                            logout = {
                                                viewModel.logout()
                                            },
                                            onEditClicked = { })
                      }, componentOnError = {})

    }
}

fun NavHostController.navigateToUserProfileScreen(navOption: NavOptions) {
    navigate(Destination.UserProfileScreen.route, navOption)
}

private fun String.withUserId(id: Int): String {
    return this.replace("{userId}", id.toString())
}

package com.sgg.cinematics.ui.screen.login

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.sgg.cinematics.ui.commonui.LoadingScreen
import com.sgg.cinematics.utils.Destination
import com.sgg.cinematics.utils.UiState
import com.sgg.cinematics.utils.validateEmail

fun NavGraphBuilder.loginScreen(navController: NavHostController) {
    composable(route = Destination.LoginScreen.route,
               enterTransition = { scaleIn() },
               exitTransition = { ExitTransition.None }
    ) {
        val loginViewModel = hiltViewModel<LoginViewModel>()
        val userData = loginViewModel.userLoginData.collectAsStateWithLifecycle()
        val loadingState = loginViewModel.loginUiState.collectAsStateWithLifecycle()

        LaunchedEffect(key1 = loadingState.value) {
            if (loadingState.value is UiState.Success) {
                navController.navigate(Destination.UserProfileScreen.route) {
                    popUpTo(Destination.LoginScreen.route) {
                        inclusive = true
                    }
                }
            }
        }

        LoginScreen(userData = userData.value,
                    isEmailValid = { validateEmail(it) },
                    updateEmail = { loginViewModel.updateEmail(it) },
                    updatePassword = { loginViewModel.updatePassword(it) },
                    login = { loginViewModel.login() },
                    onNavigateBack = {
                        navController.popBackStack(
                            route = Destination.AllMoviesScreen.route,
                            inclusive = false
                        )
                    },
                    onCreateAccountClick = { navController.navigate(Destination.CreateAccount.route) })

        AnimatedVisibility(
            visible = loadingState.value is UiState.Loading, enter = fadeIn()
        ) {
            LoadingScreen(
                modifier = Modifier.background(
                    color = Color.Black.copy(alpha = 0.6f)
                )
            )
        }
        if (loadingState.value is UiState.Error) {
            Toast.makeText(
                LocalContext.current,
                (loadingState.value as UiState.Error).error,
                Toast.LENGTH_LONG
            )
                .show()
        }
    }
}

fun NavHostController.navigateToLoginScreen() {
    val startDestinationId = this.graph.startDestinationId
    navigate(route = Destination.LoginScreen.route,
             builder = {
                 popUpTo(startDestinationId) {
                     saveState = true
                 }
             })
}
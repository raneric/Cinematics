package com.sgg.cinematics.ui.screen.details

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.firebase.auth.FirebaseUser
import com.sgg.cinematics.R
import com.sgg.cinematics.ui.CinematicsAppState
import com.sgg.cinematics.ui.components.ScreenWrapper
import com.sgg.cinematics.utils.Destination
import kotlinx.coroutines.launch

const val MOVIE_ID_ARGS = "movieId"

fun NavGraphBuilder.detailsScreen(
    cinematicsAppState: CinematicsAppState,
    connectedUser: FirebaseUser?,
) {
    composable(route = Destination.DetailScreen.route,
               arguments = listOf(navArgument(MOVIE_ID_ARGS) { type = NavType.IntType }),
               enterTransition = { fadeIn() },
               exitTransition = { fadeOut() }
    ) { navBackStackEntry ->

        val movieId = navBackStackEntry.arguments?.getInt(MOVIE_ID_ARGS)

        val detailsViewModel = hiltViewModel<DetailsViewModel>()

        var movieIsInWatchList by detailsViewModel.isInWatchList
        val detailsUiState = detailsViewModel.detailsUiState.collectAsStateWithLifecycle()
        val uiData = detailsViewModel.selectedMovie.collectAsStateWithLifecycle()
        val scope = rememberCoroutineScope()
        val loginRequestMessage = stringResource(id = R.string.login_request)
        val actionLabelLogin = stringResource(id = R.string.action_label_login)

        LaunchedEffect(Unit) {
            movieId?.let { movieId ->
                detailsViewModel.loadMovieInfo(movieId = movieId)
                detailsViewModel.loadIfInWatchList(uid = connectedUser?.uid)
            }
        }

        ScreenWrapper(uiState = detailsUiState.value,
                      componentOnSuccess = {
                          DetailsScreen(
                              movie = uiData.value!!,
                              addOrRemoveToWatchList = {
                                  if (connectedUser != null) {
                                      detailsViewModel.addOrRemoveToWatchList(connectedUser.uid)
                                  } else {
                                      scope.launch {
                                          cinematicsAppState.displaySnackBar(
                                              message = loginRequestMessage,
                                              actionLabel = actionLabelLogin
                                          ) {
                                              cinematicsAppState.navigateTo(Destination.LoginScreen)
                                          }
                                      }
                                  }
                              },
                              onRecommendationItemClicked = { movieId ->
                                  cinematicsAppState.navController.navigateToDetailsScreen(movieId = movieId)
                              },
                              onNavigateBack = {
                                  cinematicsAppState.navController.navigateUp()
                              },
                              modifier = Modifier.semantics {
                                  contentDescription = Destination.DetailScreen.testTag
                              },
                              isInWatchList = movieIsInWatchList
                          )
                      })
    }
}

fun NavHostController.navigateToDetailsScreen(
    movieId: Int,
) {
    navigate(
        route = Destination.DetailScreen.route.withMovieId(
            movieId
        )
    )
}

private fun String.withMovieId(id: Int): String {
    return this.replace("{$MOVIE_ID_ARGS}", id.toString())
}

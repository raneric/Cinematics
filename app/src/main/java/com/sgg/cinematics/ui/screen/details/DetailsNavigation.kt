package com.sgg.cinematics.ui.screen.details

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
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
import com.sgg.cinematics.ui.commonui.ScreenWrapper
import com.sgg.cinematics.ui.screen.MOVIE_ID_ARGS
import com.sgg.cinematics.utils.Destination

fun NavGraphBuilder.detailsScreen(
        navController: NavHostController,
        connectedUser: FirebaseUser?,
) {
    composable(route = Destination.DetailScreen.route,
               arguments = listOf(navArgument(MOVIE_ID_ARGS) { type = NavType.IntType }),
               enterTransition = { fadeIn() },
               exitTransition = { fadeOut() }
    ) { navBackStackEntry ->

        val movieId = navBackStackEntry.arguments?.getInt(MOVIE_ID_ARGS)

        val detailsViewModel = hiltViewModel<DetailsViewModel>()

        var movieIsInWatchList = detailsViewModel.isInWatchList
        val detailsUiState = detailsViewModel.detailsUiState.collectAsStateWithLifecycle()
        val uiData = detailsViewModel.selectedMovie.collectAsStateWithLifecycle()

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
                                      detailsViewModel.addOrRemoveToWatchList(connectedUser?.uid)
                                  },
                                  onRecommendationItemClicked = { movieId ->
                                      navigateToDetailsScreen(
                                              movieId = movieId, navController = navController
                                      )
                                  },
                                  onNavigateBack = {
                                      navController.navigateUp()
                                  },
                                  modifier = Modifier.semantics {
                                      contentDescription = Destination.DetailScreen.testTag
                                  },
                                  isInWatchList = movieIsInWatchList.value
                          )
                      }, componentOnError = { })
    }
}

fun navigateToDetailsScreen(
        movieId: Int,
        navController: NavHostController
) {
    navController.navigate(route = Destination.DetailScreen.route.addIdArgs(
            movieId))
}

private fun String.addIdArgs(id: Int): String {
    return this.replace("{movieId}", id.toString())
}

package com.sgg.cinematics.ui.screen.details

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.sgg.cinematics.ui.commonui.ScreenWrapper
import com.sgg.cinematics.ui.screen.MOVIE_ID_ARGS
import com.sgg.cinematics.utils.Destination

fun NavGraphBuilder.detailsScreen(navController: NavHostController) {
    composable(route = Destination.DetailScreen.route,
               arguments = listOf(navArgument(MOVIE_ID_ARGS) { type = NavType.IntType }),
               enterTransition = { fadeIn() },
               exitTransition = { fadeOut() }
    ) { navBackStackEntry ->

        val movieId = navBackStackEntry.arguments?.getInt(MOVIE_ID_ARGS)

        val detailsViewModel = hiltViewModel<DetailsViewModel>()
        //TODO implementation of add to watchList
        var movieIsInWatchList by remember { mutableStateOf(false) }
        val detailsUiState = detailsViewModel.detailsUiState.collectAsStateWithLifecycle()
        val uiData = detailsViewModel.selectedMovie.collectAsStateWithLifecycle()

        LaunchedEffect(Unit) {
            movieId?.let { movieId ->
                detailsViewModel.updateUiState(movieId)
            }
        }

        ScreenWrapper(uiState = detailsUiState.value,
                      componentOnSuccess = {
                          DetailsScreen(
                                  movie = uiData.value!!,
                                  addOrRemoveToWatchList = { TODO() },
                                  onRecommendationItemClicked = { movieId ->
                                      navigateToDetailsScreen(
                                              movieId = movieId, navController = navController
                                      )
                                  },
                                  modifier = Modifier.semantics {
                                      contentDescription = Destination.DetailScreen.testTag
                                  },
                                  isInWatchList = movieIsInWatchList
                          ) {
                              navController.navigateUp()
                          }
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

package com.sgg.cinematics.ui.screen

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sgg.cinematics.data.userModelLists
import com.sgg.cinematics.ui.commonui.LoadingScreen
import com.sgg.cinematics.ui.screen.details.DetailsScreen
import com.sgg.cinematics.ui.screen.details.DetailsViewModel
import com.sgg.cinematics.ui.screen.login.LoginScreen
import com.sgg.cinematics.ui.screen.movieList.MovieListScreen
import com.sgg.cinematics.ui.screen.movieList.MovieListViewModel
import com.sgg.cinematics.ui.screen.userProfile.UserProfileScreen
import com.sgg.cinematics.utils.Destination
import com.sgg.cinematics.utils.MovieListUiMode
import com.sgg.cinematics.utils.UiData
import com.sgg.cinematics.utils.UiState
import com.sgg.cinematics.utils.navigateToDetailsScreen

// TODO: Refactoring for this composable function
@Composable
fun CinematicsNavHost(
        navController: NavHostController,
        movieListViewModel: MovieListViewModel,
        movieListUiMode: MovieListUiMode,
        windowsWidthSizeClass: WindowWidthSizeClass,
        modifier: Modifier = Modifier
) {

    val detailsViewModel = hiltViewModel<DetailsViewModel>()

    val listViewModel = hiltViewModel<MovieListViewModel>()
    val watchList = movieListViewModel.watchList

    val detailsUiState = detailsViewModel.detailsUiState.collectAsStateWithLifecycle()
    val listUiState = listViewModel.listUiState.collectAsStateWithLifecycle()

    val connectedUser = listViewModel.connectedUser?.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = Destination.TrendingScreen.route,
        modifier = modifier) {

        composable(route = Destination.TrendingScreen.route) {
            ScreenWrapper(
                uiState = listUiState.value,
                componentOnSuccess = {
                    val uiData = (listUiState.value as UiState.Success).uiData as UiData.ListScreenData
                    val trendingMovies = uiData.movieList.collectAsStateWithLifecycle(initialValue = emptyList())
                    MovieListScreen(movieListUiMode = movieListUiMode,
                                    movieList = trendingMovies.value,
                                    navController = navController,
                                    windowsWidthSizeClass = windowsWidthSizeClass,
                                    modifier = Modifier.semantics {
                                        contentDescription = Destination.TrendingScreen.testTag
                                    }) { movieId ->
                        detailsViewModel.updateUiState(movieId)
                    }
                }, componentOnError = { /*TODO*/ })
        }

        composable(route = Destination.TopRatedScreen.route) {
            ScreenWrapper(
                uiState = listUiState.value,
                componentOnSuccess = {
                    val uiData = (listUiState.value as UiState.Success).uiData as UiData.ListScreenData
                    val topRated = uiData.movieList.collectAsStateWithLifecycle(initialValue = emptyList())
                    MovieListScreen(movieListUiMode = movieListUiMode,
                                    movieList = topRated.value,
                                    navController = navController,
                                    windowsWidthSizeClass = windowsWidthSizeClass,
                                    modifier = Modifier.semantics {
                                        contentDescription = Destination.TrendingScreen.testTag
                                    }) { movieId ->
                        detailsViewModel.updateUiState(movieId)
                    }
                }, componentOnError = { /*TODO*/ })
        }

        composable(route = Destination.WatchListScreen.route) {
            MovieListScreen(movieListUiMode = movieListUiMode,
                            movieList = watchList,
                            navController = navController,
                            windowsWidthSizeClass = windowsWidthSizeClass,
                            modifier = Modifier.semantics {
                                contentDescription = Destination.WatchListScreen.testTag
                            }) { movieId ->
                detailsViewModel.updateUiState(movieId)
            }
        }

        composable(route = Destination.DetailScreen.route,
                   arguments = listOf(navArgument(MOVIE_ID_ARGS) { type = NavType.IntType })) { backStackEntry ->

            var movieIsInWatchList by remember { mutableStateOf(false) }

            LaunchedEffect(Unit) {
                detailsViewModel.isInWatchList.collect { isInWatchList ->
                    movieIsInWatchList = isInWatchList
                }
            }

            ScreenWrapper(
                uiState = detailsUiState.value,
                componentOnSuccess = {
                    val uiData = (detailsUiState.value as UiState.Success).uiData as UiData.DetailScreenData
                    DetailsScreen(
                        movie = uiData.movie,
                        addOrRemoveToWatchList = { TODO() },
                        onRecommendationItemClicked = { movieId ->
                            navigateToDetailsScreen(
                                movieId = movieId, navController = navController)
                        },
                        modifier = Modifier.semantics {
                            contentDescription = Destination.DetailScreen.testTag
                        },
                        isInWatchList = movieIsInWatchList) {
                        navController.navigateUp()
                    }
                }, componentOnError = {/*TODO*/ })
        }

        composable(route = Destination.UserProfileScreen.route) {
            if (connectedUser == null) {
                LoginScreen()
            } else {
                UserProfileScreen(user = userModelLists[0])
            }
        }

        composable(route = Destination.LoginScreen.route) {
            LoginScreen()
        }
    }
}


/**
 * This composable wrap a screen and display component toward uiState :
 *  - [UiState.Success] : show component [componentOnSuccess] param
 *  - [UiState.Loading] : Show spinning progress
 *  - [UiState.Error] : show snackbar and error page with [componentOnError] param
 *
 * @param uiState : the current ui state
 * @param componentOnSuccess : component to display on success
 * @param componentOnError : component to display on error
 * @param modifier: A modifier with default value [Modifier]
 */
@Composable
fun ScreenWrapper(
        uiState: UiState,
        modifier: Modifier = Modifier,
        componentOnSuccess: @Composable () -> Unit,
        componentOnError: @Composable () -> Unit,

        ) {
    if (uiState is UiState.Loading) {
        LoadingScreen()
    }

    if (uiState is UiState.Success) {
        componentOnSuccess()
    }

    if (uiState is UiState.Error) {
        componentOnError()
    }
}

const val MOVIE_ID_ARGS = "movieId"
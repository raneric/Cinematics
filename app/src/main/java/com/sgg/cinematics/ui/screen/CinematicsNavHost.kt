package com.sgg.cinematics.ui.screen

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.sgg.cinematics.ui.screen.account.CreateAccountScreen
import com.sgg.cinematics.ui.screen.account.UserProfileScreen
import com.sgg.cinematics.ui.screen.details.DetailsScreen
import com.sgg.cinematics.ui.screen.details.DetailsViewModel
import com.sgg.cinematics.ui.screen.login.LoginScreen
import com.sgg.cinematics.ui.screen.login.LoginViewModel
import com.sgg.cinematics.ui.screen.movieList.MovieListScreen
import com.sgg.cinematics.ui.screen.movieList.MovieListViewModel
import com.sgg.cinematics.utils.Destination
import com.sgg.cinematics.utils.MovieListUiMode
import com.sgg.cinematics.utils.UiState
import com.sgg.cinematics.utils.navigateToDetailsScreen
import com.sgg.cinematics.utils.validateEmail

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
    val loginViewModel = hiltViewModel<LoginViewModel>()
    val listViewModel = hiltViewModel<MovieListViewModel>()

    val watchList = movieListViewModel.watchList

    val movieList = movieListViewModel.movieList.collectAsStateWithLifecycle(initialValue = emptyList())

    val listUiState = listViewModel.listUiState.collectAsStateWithLifecycle()

    val connectedUser = listViewModel.connectedUser.collectAsStateWithLifecycle(initialValue = null)

    NavHost(
        navController = navController,
        startDestination = Destination.TrendingScreen.route,
        modifier = modifier) {

        composable(route = Destination.TrendingScreen.route) {
            ScreenWrapper(uiState = listUiState.value, componentOnSuccess = {
                MovieListScreen(movieListUiMode = movieListUiMode,
                                movieList = movieList.value,
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
            ScreenWrapper(uiState = listUiState.value, componentOnSuccess = {
                MovieListScreen(movieListUiMode = movieListUiMode,
                                movieList = movieList.value,
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
                   arguments = listOf(navArgument(MOVIE_ID_ARGS) { type = NavType.IntType })) {

            var movieIsInWatchList by remember { mutableStateOf(false) }
            val detailsUiState = detailsViewModel.detailsUiState.collectAsStateWithLifecycle()
            val uiData = detailsViewModel.selectedMovie.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                detailsViewModel.isInWatchList.collect { isInWatchList ->
                    movieIsInWatchList = isInWatchList
                }
            }

            ScreenWrapper(
                uiState = detailsUiState.value,
                componentOnSuccess = {
                    DetailsScreen(
                        movie = uiData.value!!,
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
                }, componentOnError = { })
        }

        composable(route = Destination.UserProfileScreen.route) {

            if (connectedUser.value == null) {
                navController.navigate(Destination.LoginScreen.route)
            } else {
                UserProfileScreen(user = userModelLists[0]) {
                    loginViewModel.logout()
                }
            }
        }

        composable(route = Destination.LoginScreen.route) {
            val userData = loginViewModel.userLoginData.collectAsStateWithLifecycle()
            val isEmailValid = loginViewModel.isEmailValid.collectAsStateWithLifecycle()
            val loadingState = loginViewModel.loginUiState.collectAsStateWithLifecycle()

            LoginScreen(userData = userData.value,
                        isEmailValid = { validateEmail(it) },
                        updateEmail = { loginViewModel.updateEmail(it) },
                        updatePassword = { loginViewModel.updatePassword(it) },
                        login = { loginViewModel.login(navController) },
                        onNavigateBack = { navController.navigate(Destination.TrendingScreen.route) },
                        onCreateAccountClick = { navController.navigate(Destination.CreateAccount.route) })

            if (loadingState.value is UiState.Error) {
                Toast.makeText(
                    LocalContext.current,
                    (loadingState.value as UiState.Error).error,
                    Toast.LENGTH_LONG)
                    .show()
            }

            AnimatedVisibility(
                visible = loadingState.value is UiState.Loading, enter = fadeIn()) {
                LoadingScreen(
                    modifier = Modifier.background(
                        color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f)))
            }
        }

        composable(route = Destination.CreateAccount.route) {
            CreateAccountScreen()
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
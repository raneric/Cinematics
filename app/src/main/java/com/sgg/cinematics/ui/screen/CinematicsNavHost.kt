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
import com.google.firebase.auth.FirebaseUser
import com.sgg.cinematics.data.model.MovieModel
import com.sgg.cinematics.data.userModelLists
import com.sgg.cinematics.domaine.UserDataValidatorUseCase.validateEmail
import com.sgg.cinematics.ui.commonui.LoadingScreen
import com.sgg.cinematics.ui.commonui.ScreenWrapper
import com.sgg.cinematics.ui.screen.account.CreateAccountScreen
import com.sgg.cinematics.ui.screen.account.UserProfileScreen
import com.sgg.cinematics.ui.screen.details.DetailsScreen
import com.sgg.cinematics.ui.screen.details.DetailsViewModel
import com.sgg.cinematics.ui.screen.login.LoginScreen
import com.sgg.cinematics.ui.screen.login.LoginViewModel
import com.sgg.cinematics.ui.screen.movieList.MovieListScreen
import com.sgg.cinematics.utils.Destination
import com.sgg.cinematics.utils.MovieListUiMode
import com.sgg.cinematics.utils.UiState
import com.sgg.cinematics.utils.navigateToDetailsScreen

// TODO: Refactoring for this composable function
@Composable
fun CinematicsNavHost(
        navController: NavHostController,
        movieListUiMode: MovieListUiMode,
        movies: List<MovieModel>,
        listUiState: UiState,
        connectedUser: FirebaseUser?,
        windowsWidthSizeClass: WindowWidthSizeClass,
        modifier: Modifier = Modifier
) {

    val loginViewModel = hiltViewModel<LoginViewModel>()

    NavHost(
        navController = navController,
        startDestination = Destination.TrendingScreen.route,
        modifier = modifier) {

        composable(route = Destination.TrendingScreen.route) {
            ScreenWrapper(uiState = listUiState, componentOnSuccess = {
                MovieListScreen(movieListUiMode = movieListUiMode,
                                movieList = movies,
                                navController = navController,
                                windowsWidthSizeClass = windowsWidthSizeClass,
                                modifier = Modifier.semantics {
                                    contentDescription = Destination.TrendingScreen.testTag
                                })
            }, componentOnError = { /*TODO*/ })
        }

        composable(route = Destination.TopRatedScreen.route) {
            ScreenWrapper(uiState = listUiState, componentOnSuccess = {
                MovieListScreen(movieListUiMode = movieListUiMode,
                                movieList = movies,
                                navController = navController,
                                windowsWidthSizeClass = windowsWidthSizeClass,
                                modifier = Modifier.semantics {
                                    contentDescription = Destination.TrendingScreen.testTag
                                })
            }, componentOnError = { /*TODO*/ })
        }

        composable(route = Destination.WatchListScreen.route) {
            MovieListScreen(movieListUiMode = movieListUiMode,
                            movieList = movies,
                            navController = navController,
                            windowsWidthSizeClass = windowsWidthSizeClass,
                            modifier = Modifier.semantics {
                                contentDescription = Destination.WatchListScreen.testTag
                            })
        }

        composable(
            route = Destination.DetailScreen.route,
            arguments = listOf(navArgument(MOVIE_ID_ARGS) { type = NavType.IntType })) { navBackStackEntry ->

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

            if (connectedUser == null) {
                navController.navigate(Destination.LoginScreen.route)
            } else {
                UserProfileScreen(user = userModelLists[0]) {
                    loginViewModel.logout()
                }
            }
        }

        composable(route = Destination.LoginScreen.route) {
            val userData = loginViewModel.userLoginData.collectAsStateWithLifecycle()
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
            CreateAccountScreen() {

            }
        }
    }
}

const val MOVIE_ID_ARGS = "movieId"
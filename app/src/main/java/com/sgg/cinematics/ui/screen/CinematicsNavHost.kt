package com.sgg.cinematics.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sgg.cinematics.data.model.MovieModel
import com.sgg.cinematics.ui.MainViewModel
import com.sgg.cinematics.utils.Destination
import com.sgg.cinematics.utils.MovieListUiMode

// TODO: Refactoring for this composable function
@Composable
fun CinematicsNavHost(navController: NavHostController,
                      viewModel: MainViewModel,
                      movieListUiMode: MovieListUiMode,
                      windowsWidthSizeClass: WindowWidthSizeClass,
                      modifier: Modifier = Modifier) {

    val trendingMovies = viewModel.trendingMovies.collectAsStateWithLifecycle(initialValue = emptyList())
    val topRatedMovies = viewModel.topRatedMovies.collectAsStateWithLifecycle(initialValue = emptyList())
    val watchList = viewModel.watchList

    NavHost(navController = navController,
            startDestination = Destination.TrendingScreen.route,
            modifier = modifier) {

        composable(route = Destination.TrendingScreen.route) {
            MovieListScreen(movieListUiMode = movieListUiMode,
                            movieList = trendingMovies.value,
                            navController = navController,
                            windowsWidthSizeClass = windowsWidthSizeClass,
                            modifier = Modifier.semantics {
                                contentDescription = Destination.TrendingScreen.testTag
                            })
        }

        composable(route = Destination.TopRatedScreen.route) {
            MovieListScreen(movieListUiMode = movieListUiMode,
                            movieList = topRatedMovies.value,
                            navController = navController,
                            windowsWidthSizeClass = windowsWidthSizeClass,
                            modifier = Modifier.semantics {
                                contentDescription = Destination.TopRatedScreen.testTag
                            })
        }

        composable(route = Destination.WatchListScreen.route) {
            MovieListScreen(movieListUiMode = movieListUiMode,
                            movieList = watchList,
                            navController = navController,
                            windowsWidthSizeClass = windowsWidthSizeClass,
                            modifier = Modifier.semantics {
                                contentDescription = Destination.WatchListScreen.testTag
                            })
        }

        composable(route = Destination.DetailScreen.route,
                   arguments = listOf(navArgument(MOVIE_ID_ARGS) { type = NavType.IntType })) { backStackEntry ->
            val movie = viewModel.getMovie(backStackEntry.arguments?.getInt(MOVIE_ID_ARGS)!!)
            var movieIsInWatchList by remember { mutableStateOf(false) }

            LaunchedEffect(Unit) {
                viewModel.isInWatchList
                        .collect {
                            movieIsInWatchList = it
                        }
            }
            DetailsScreen(
                movie = movie,
                addOrRemoveToWatchList = {
                    if (movieIsInWatchList) {
                        viewModel.removeToWatchList(movie)
                    } else {
                        viewModel.addToWatchList(movie)
                    }
                },
                onRecommendationItemClicked = {
                    navigateToDetailsScreen(movieId = it, navController = navController)
                },
                modifier = Modifier.semantics {
                    contentDescription = Destination.DetailScreen.testTag
                },
                isInWatchList = movieIsInWatchList) {
                navController.navigateUp()
            }
        }

        composable(route = Destination.UserProfileScreen.route) {
            UserProfileScreen()
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MovieListScreen(movieListUiMode: MovieListUiMode,
                    movieList: List<MovieModel>,
                    navController: NavHostController,
                    windowsWidthSizeClass: WindowWidthSizeClass,
                    modifier: Modifier = Modifier) {

    if (windowsWidthSizeClass == WindowWidthSizeClass.Compact) {
        AnimatedVisibility(visible = movieListUiMode is MovieListUiMode.ListView,
                           enter = scaleIn(),
                           exit = fadeOut()) {
            VerticalMovieListScreen(movieList = movieList,
                                    modifier = modifier.testTag(movieListUiMode.testTag)) { movieId ->
                navigateToDetailsScreen(movieId = movieId, navController = navController)
            }
        }
        AnimatedVisibility(visible = movieListUiMode is MovieListUiMode.CarouselView,
                           enter = scaleIn(),
                           exit = fadeOut()) {
            HorizontalMovieListScreen(movieList = movieList,
                                      modifier = modifier.testTag(movieListUiMode.testTag)) { movieId ->
                navigateToDetailsScreen(movieId = movieId, navController = navController)
            }
        }
    } else {
        AnimatedVisibility(visible = movieListUiMode is MovieListUiMode.CarouselView,
                           enter = scaleIn(),
                           exit = fadeOut()) {
            GridMovieListScreen(movieList = movieList) { movieId ->
                navigateToDetailsScreen(movieId = movieId, navController = navController)
            }
        }
    }
}

private fun navigateToDetailsScreen(movieId: Int,
                                    navController: NavHostController) {
    navController.navigate(route = Destination.DetailScreen.route.addIdArgs(
        movieId))
}

private fun String.addIdArgs(id: Int): String {
    return this.replace("{movieId}", id.toString())
}

const val MOVIE_ID_ARGS = "movieId"
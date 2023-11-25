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
import com.sgg.cinematics.ui.screen.details.DetailsScreen
import com.sgg.cinematics.ui.screen.details.DetailsViewModel
import com.sgg.cinematics.ui.screen.movieList.MovieListScreen
import com.sgg.cinematics.ui.screen.movieList.MovieListViewModel
import com.sgg.cinematics.ui.screen.userProfile.UserProfileScreen
import com.sgg.cinematics.utils.Destination
import com.sgg.cinematics.utils.MovieListUiMode
import com.sgg.cinematics.utils.navigateToDetailsScreen

// TODO: Refactoring for this composable function
@Composable
fun CinematicsNavHost(navController: NavHostController,
                      movieListViewModel: MovieListViewModel,
                      movieListUiMode: MovieListUiMode,
                      windowsWidthSizeClass: WindowWidthSizeClass,
                      modifier: Modifier = Modifier) {

    val trendingMovies = movieListViewModel.trendingMovies.collectAsStateWithLifecycle(initialValue = emptyList())
    val topRatedMovies = movieListViewModel.topRatedMovies.collectAsStateWithLifecycle(initialValue = emptyList())
    val watchList = movieListViewModel.watchList

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

            val detailsViewModel = hiltViewModel<DetailsViewModel>()
            // FIXME: Use null safe
            val movie = detailsViewModel.getMovie(backStackEntry.arguments?.getInt(MOVIE_ID_ARGS)!!)!!
            var movieIsInWatchList by remember { mutableStateOf(false) }

            LaunchedEffect(Unit) {
                detailsViewModel.isInWatchList
                        .collect { isInWatchList ->
                            movieIsInWatchList = isInWatchList
                        }
            }
            DetailsScreen(
                movie = movie,
                addOrRemoveToWatchList = {
                    if (movieIsInWatchList) {
                        detailsViewModel.removeToWatchList(movie)
                    } else {
                        detailsViewModel.addToWatchList(movie)
                    }
                },
                onRecommendationItemClicked = { movieId ->
                    navigateToDetailsScreen(movieId = movieId, navController = navController)
                },
                modifier = Modifier.semantics {
                    contentDescription = Destination.DetailScreen.testTag
                },
                isInWatchList = movieIsInWatchList) {
                navController.navigateUp()
            }
        }

        composable(route = Destination.UserProfileScreen.route) {
            UserProfileScreen(user = userModelLists[0])
        }
    }
}

const val MOVIE_ID_ARGS = "movieId"
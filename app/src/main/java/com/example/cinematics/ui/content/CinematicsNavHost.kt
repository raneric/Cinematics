package com.example.cinematics.ui.content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.cinematics.data.model.MovieModel
import com.example.cinematics.ui.MainViewModel
import com.example.cinematics.utils.CinematicsDestination

@Composable
fun CinematicsNavHost(navController: NavHostController,
                      viewModel: MainViewModel,
                      modifier: Modifier = Modifier,
                      isCurrentScreenDetailScreen: (Boolean) -> Unit) {
    val trendingMovies = viewModel.trendingMovies.collectAsState(initial = emptyList())
    val topRatedMovies = viewModel.topRatedMovies.collectAsState(initial = emptyList())
    val watchList = viewModel.watchList

    NavHost(navController = navController,
            startDestination = CinematicsDestination.TRENDING.route,
            modifier = modifier) {

        composable(route = CinematicsDestination.TRENDING.route) {
            isCurrentScreenDetailScreen(false)
            TrendingScreenDestination(trendingMovies.value, navController)
        }

        composable(route = CinematicsDestination.TOP_RATED.route) {
            isCurrentScreenDetailScreen(false)
            TopRatedScreenDestination(topRatedMovies = topRatedMovies.value,
                                      navController = navController)
        }

        composable(route = CinematicsDestination.WATCH_LIST.route) {
            isCurrentScreenDetailScreen(false)
            WatchListScreenDestination(watchList = watchList, navController = navController)
        }

        composable(route = CinematicsDestination.DETAILS_SCREEN.route,
                   arguments = listOf(navArgument(MOVIE_ID_ARGS) { type = NavType.IntType })) { backStackEntry ->
            isCurrentScreenDetailScreen(true)
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
                isInWatchList = movieIsInWatchList) {
                navController.navigateUp()
            }
        }
    }
}


@Composable
fun TrendingScreenDestination(trendingMovies: List<MovieModel>,
                              navController: NavHostController) {
    VerticalMovieListScreen(movieList = trendingMovies) { movieId ->
        navigateToDetailsScreen(movieId = movieId, navController = navController)
    }
}

@Composable
fun TopRatedScreenDestination(topRatedMovies: List<MovieModel>,
                              navController: NavHostController) {
    VerticalMovieListScreen(movieList = topRatedMovies) { movieId ->
        navigateToDetailsScreen(movieId = movieId, navController = navController)
    }
}

@Composable
fun WatchListScreenDestination(watchList: List<MovieModel>,
                               navController: NavHostController) {
    VerticalMovieListScreen(movieList = watchList) { movieId ->
        navigateToDetailsScreen(movieId = movieId, navController = navController)
    }
}

private fun navigateToDetailsScreen(movieId: Int,
                                    navController: NavHostController) {
    navController.navigate(route = CinematicsDestination.DETAILS_SCREEN.route.addIdArgs(
        movieId))
}

private fun String.addIdArgs(id: Int): String {
    return this.replace("{movieId}", id.toString())
}

const val MOVIE_ID_ARGS = "movieId"
package com.example.cinematics.ui.content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.cinematics.ui.MainViewModel
import com.example.cinematics.utils.CinematicsDestination
import kotlinx.coroutines.delay

@Composable
fun CinematicsNavHost(navController: NavHostController,
                      viewModel: MainViewModel,
                      modifier: Modifier = Modifier,
                      onDetailScreenListener: (Boolean) -> Unit) {
    val trendingMovies = viewModel.trendingMovies.collectAsState(initial = emptyList())
    val topRatedMovies = viewModel.topRatedMovies.collectAsState(initial = emptyList())
    val watchList = viewModel.watchList

    NavHost(navController = navController,
            startDestination = CinematicsDestination.TRENDING.route,
            modifier = modifier) {

        composable(route = CinematicsDestination.TRENDING.route) {
            onDetailScreenListener(false)
            HorizontalMovieListScreen(movieList = trendingMovies.value) { movieId ->
                navController.navigate(route = CinematicsDestination.DETAILS_SCREEN.route.addIdArgs(
                    movieId))
            }
        }

        composable(route = CinematicsDestination.TOP_RATED.route) {
            onDetailScreenListener(false)
            VerticalMovieListScreen(movieList = topRatedMovies.value) { movieId ->
                navController.navigate(route = CinematicsDestination.DETAILS_SCREEN.route.addIdArgs(
                    movieId))
            }
        }

        composable(route = CinematicsDestination.WATCH_LIST.route) {
            onDetailScreenListener(false)
            VerticalMovieListScreen(movieList = watchList) { movieId ->
                navController.navigate(route = CinematicsDestination.DETAILS_SCREEN.route.addIdArgs(
                    movieId))
            }
        }

        composable(route = CinematicsDestination.DETAILS_SCREEN.route,
                   arguments = listOf(navArgument(MOVIE_ID_ARGS) { type = NavType.IntType })) { backStackEntry ->
            onDetailScreenListener(true)
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
                addOrRemoveWatchList = {
                    if (movieIsInWatchList) {
                        viewModel.removeToWatchList(movie)
                    } else {
                        viewModel.addToWatchList(movie)
                    }
                },
                isInWatchList = movieIsInWatchList) {
                navController.navigateUp()
            }
        }
    }
}


private fun String.addIdArgs(id: Int): String {
    return this.replace("{movieId}", id.toString())
}

const val MOVIE_ID_ARGS = "movieId"
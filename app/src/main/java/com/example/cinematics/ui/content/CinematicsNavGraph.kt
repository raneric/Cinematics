package com.example.cinematics.ui.content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.cinematics.ui.MainViewModel
import com.example.cinematics.utils.CinematicsDestination

@Composable
fun CinematicsNavGraph(
        navController: NavHostController,
        viewModel: MainViewModel,
        modifier: Modifier = Modifier,
        onDetailScreenListener: (Boolean) -> Unit
) {
    val movieList = viewModel.movies.collectAsState(initial = emptyList())
    NavHost(navController = navController,
            startDestination = CinematicsDestination.TRENDING.route,
            modifier = modifier) {

        composable(route = CinematicsDestination.TRENDING.route) {
            onDetailScreenListener(false)
            MovieListScreen(movieList = movieList.value) { movieId ->
                navController.navigate(
                    route = CinematicsDestination.DETAILS_SCREEN.route.addIdArgs(movieId))
            }
        }

        composable(route = CinematicsDestination.DETAILS_SCREEN.route,
                   arguments = listOf(navArgument(MOVIE_ID_ARGS) { type = NavType.IntType })) { backStackEntry ->
            onDetailScreenListener(true)
            val movie = viewModel.getMovie(backStackEntry.arguments?.getInt(MOVIE_ID_ARGS)!!)
            DetailsScreen(movie = movie) {
                navController.navigateUp()
            }
        }
    }
}

private fun String.addIdArgs(id: Int): String {
    return this.replace("{movieId}", id.toString())
}

const val MOVIE_ID_ARGS = "movieId"
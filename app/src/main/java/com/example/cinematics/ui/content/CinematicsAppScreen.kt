package com.example.cinematics.ui.content

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cinematics.ui.MainViewModel
import com.example.cinematics.ui.components.BottomNavScreen
import com.example.cinematics.ui.components.bottomNavItemList
import com.example.cinematics.utils.CinematicsDestination

@Composable
fun CinematicsAppScreen(viewModel: MainViewModel) {

    val navController = rememberNavController()
    var showBottomNav: Boolean by remember { mutableStateOf(true) }

    val movieList = viewModel.movies.collectAsState(initial = emptyList())

    Scaffold(
        bottomBar = {
            AnimatedVisibility(visible = showBottomNav) {
                BottomNavScreen(bottomNavItemList)
            }
        }
    ) { paddingValue ->
        NavHost(navController = navController,
                startDestination = CinematicsDestination.TRENDING.route,
                modifier = Modifier.padding(paddingValue)) {

            composable(route = CinematicsDestination.TRENDING.route) {
                showBottomNav = true
                MovieListScreen(movieList = movieList.value) { movieId ->
                    navController.navigate(route = "${CinematicsDestination.DETAILS_SCREEN.route}/$movieId")
                }
            }

            composable(route = "${CinematicsDestination.DETAILS_SCREEN.route}/{$MOVIE_ID_ARGS}",
                       arguments = listOf(navArgument(MOVIE_ID_ARGS) { type = NavType.IntType })) { backStackEntry ->
                showBottomNav = false
                val movie = viewModel.getMovie(backStackEntry.arguments?.getInt(MOVIE_ID_ARGS)!!)
                DetailsScreen(movie = movie) {
                    navController.navigateUp()
                }
            }
        }
    }
}

const val MOVIE_ID_ARGS = "movieId"
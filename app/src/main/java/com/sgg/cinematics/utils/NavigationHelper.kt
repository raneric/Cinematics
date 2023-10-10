package com.sgg.cinematics.utils

import androidx.navigation.NavHostController

fun navigateToDetailsScreen(movieId: Int,
                            navController: NavHostController) {
    navController.navigate(route = Destination.DetailScreen.route.addIdArgs(
        movieId))
}

private fun String.addIdArgs(id: Int): String {
    return this.replace("{movieId}", id.toString())
}
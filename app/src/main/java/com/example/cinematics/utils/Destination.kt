package com.example.cinematics.utils

sealed class Destination(val route: String,
                         val testTag: String) {

    object TrendingScreen : Destination(route = "trending", testTag = "trending_screen")
    object TopRatedScreen : Destination(route = "top_rated", testTag = "top_rated_screen")
    object WatchListScreen : Destination(route = "watch_list", testTag = "watch_list_screen")
    object DetailScreen : Destination(route = "details/{movieId}", testTag = "details_screen")
}

package com.sgg.cinematics.utils

sealed class Destination(
        val route: String,
        val testTag: String
) {

    object TrendingScreen : Destination(route = "trending", testTag = "trending_screen")
    object WatchListScreen : Destination(route = "watch_list", testTag = "watch_list_screen")
    object DetailScreen : Destination(route = "details/{movieId}", testTag = "details_screen")
    object UserProfileScreen : Destination(route = "user_profile", testTag = "user_profile_screen")
    object LoginScreen : Destination(route = "login", testTag = "login_screen")
    object CreateAccount : Destination(route = "create_account", testTag = "create_account_screen")
}

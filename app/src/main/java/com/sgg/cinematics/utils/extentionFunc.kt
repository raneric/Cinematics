package com.sgg.cinematics.utils

import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import com.sgg.cinematics.ui.components.NavItemVariant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")

val LocalDate.formattedStringDate: String
    get() {
        return this.format(dateFormatter)
    }

fun LocalDateTime.currentDateAsString(): String {
    return "${this.dayOfMonth}${this.monthValue}${this.year}${this.hour}${this.minute}${this.second}"
}

fun Long.millisToLocalDate(): LocalDate {
    return LocalDate.ofEpochDay(this / 1000 / 60 / 60 / 24)
}

fun LocalDate.toMillis(): Long {
    return this.toEpochDay() * 1000 * 60 * 60 * 24
}


/**
 * Extension function for [NavDestination] that will return a [NavItemVariant]
 * depending on the selected destination
 */
fun NavDestination.activeNavItem(): NavItemVariant {
    return when (this.route) {
        Destination.WatchListScreen.route   -> NavItemVariant.WatchList
        Destination.UserProfileScreen.route -> NavItemVariant.UserProfile
        else                                -> NavItemVariant.Trending
    }
}

fun NavDestination.isInBottomNavDestination(): Boolean {
    return this.route == Destination.TrendingScreen.route ||
           this.route == Destination.WatchListScreen.route ||
           this.route == Destination.UserProfileScreen.route
}

fun NavDestination.isIntListDestination(): Boolean {
    return this.route == Destination.TrendingScreen.route ||
           this.route == Destination.WatchListScreen.route
}

fun NavHostController.navigateIfNotMovieList(navItem: NavItemVariant) {
    if (!navItem.isInMovieList()) {
        navigate(navItem.destination.route)
    }
}

fun NavItemVariant.isInMovieList(): Boolean {
    return this == NavItemVariant.Trending ||
           this == NavItemVariant.WatchList
}
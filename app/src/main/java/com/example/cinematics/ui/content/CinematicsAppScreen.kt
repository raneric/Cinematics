package com.example.cinematics.ui.content

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.cinematics.ui.MainViewModel
import com.example.cinematics.ui.components.BottomNavScreen
import com.example.cinematics.ui.components.bottomNavItemList

@Composable
fun CinematicsAppScreen(viewModel: MainViewModel) {

    val navController = rememberNavController()
    var showBottomNav: Boolean by remember { mutableStateOf(true) }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(visible = showBottomNav,
                               enter = slideInVertically(initialOffsetY = { -40 })) {
                BottomNavScreen(bottomNavItemList)
            }
        }
    ) { paddingValue ->
        CinematicsNavGraph(navController = navController,
                           viewModel = viewModel,
                           modifier = Modifier.padding(paddingValue)) {
            showBottomNav = !it
        }
    }
}

private fun String.isDetailsScreenRoute(): Boolean {
    return this.matches("details\\/\\{movieId\\}".toRegex())
}

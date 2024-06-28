package com.sgg.cinematics.ui.screen

import android.graphics.Shader
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sgg.cinematics.ui.CinematicsAppState
import com.sgg.cinematics.ui.MainViewModel
import com.sgg.cinematics.ui.components.CinematicsNavigationBar
import com.sgg.cinematics.ui.components.CinematicsNavigationRail
import com.sgg.cinematics.ui.screen.movieList.MovieListViewModel
import com.sgg.cinematics.ui.ui.theme.gradient_inside_color
import com.sgg.cinematics.ui.ui.theme.gradient_outside_color
import kotlinx.collections.immutable.toImmutableList

@Composable
fun CinematicsAppScreen(
    modifier: Modifier = Modifier,
    cinematicsAppState: CinematicsAppState
) {
    val mainViewModel = hiltViewModel<MainViewModel>()
    val movieListViewModel = hiltViewModel<MovieListViewModel>()

    val connectedUser by mainViewModel.connectedUser.collectAsStateWithLifecycle(initialValue = null)
    val movieList by movieListViewModel.movies.collectAsStateWithLifecycle()

    cinematicsAppState.navController.addOnDestinationChangedListener { _, destination, _ ->
        destination.route?.let {
            movieListViewModel.loadRequiredMovieList(it)
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(hostState = cinematicsAppState.snackbarHostState)
        },
        bottomBar = {
            AnimatedVisibility(
                visible = cinematicsAppState.shouldShowBottomNav,
                enter = slideInVertically(initialOffsetY = { -40 })
            ) {
                CinematicsNavigationBar(
                    activeNavItem = cinematicsAppState.activeNavItem,
                    onDestinationChanged = cinematicsAppState::navigateTo
                )
            }
        }) { paddingValue ->

        Row(modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                drawRect(largeRadialGradient)
            }
            .padding(paddingValue)) {
            if (cinematicsAppState.shouldShowNavRail) {
                CinematicsNavigationRail(
                    activeNavItem = cinematicsAppState.activeNavItem,
                    onDestinationChanged = cinematicsAppState::navigateTo
                )
            }
            CinematicsNavHost(
                cinematicsAppState = cinematicsAppState,
                connectedUser = connectedUser,
                movieList = movieList.toImmutableList(),
                movieListViewModel = movieListViewModel
            )
        }
    }
}

private val largeRadialGradient = object : ShaderBrush() {
    override fun createShader(size: Size): Shader {
        val biggerDimension = maxOf(size.height, size.width)
        return RadialGradientShader(
            colors = listOf(gradient_inside_color, gradient_outside_color),
            center = size.center,
            radius = biggerDimension / 2f,
            colorStops = listOf(0f, 1f)
        )
    }
}
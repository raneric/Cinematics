package com.example.cinematics.ui.content

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.cinematics.data.movieList
import com.example.cinematics.ui.components.BottomNavScreen
import com.example.cinematics.ui.components.bottomNavItemList
import com.example.cinematics.ui.ui.theme.CinematicsTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CinematicsAppScreen() {
    Scaffold(
        bottomBar = { BottomNavScreen(bottomNavItemList) }
    ) { paddingValue ->
        ContentScreen(modifier = Modifier.padding(paddingValue))
    }
}

@Composable
fun ContentScreen(modifier: Modifier = Modifier) {
    MovieListScreen(movieList = movieList, modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun CinematicsAppPreview() {
    CinematicsTheme {
        CinematicsAppScreen()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES,
         showBackground = true)
@Composable
fun CinematicsAppPreviewDark() {
    CinematicsTheme {
        CinematicsAppScreen()
    }
}

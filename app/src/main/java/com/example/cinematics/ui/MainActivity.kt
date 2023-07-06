package com.example.cinematics.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.cinematics.data.movieList
import com.example.cinematics.ui.components.BottomNavScreen
import com.example.cinematics.ui.components.Overview
import com.example.cinematics.ui.components.bottomNavItemList
import com.example.cinematics.ui.content.DetailsScreen
import com.example.cinematics.ui.content.MovieListScreen
import com.example.cinematics.ui.ui.theme.CinematicsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CinematicsTheme {
                DetailsScreen(movie = movieList[0])
                // CinematicsApp()
            }
        }
    }
}

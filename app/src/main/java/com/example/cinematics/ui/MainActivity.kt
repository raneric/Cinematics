package com.example.cinematics.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.cinematics.ui.appbar.AppBar
import com.example.cinematics.ui.bottomnav.BottomNavScreen
import com.example.cinematics.ui.bottomnav.bottomNavItemList
import com.example.cinematics.ui.components.Genre
import com.example.cinematics.ui.ui.theme.CinematicsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CinematicsTheme {
                CinematicsApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CinematicsApp() {
    Scaffold(
        topBar = { AppBar() },
        bottomBar = { BottomNavScreen(bottomNavItemList) }
    ) { padding ->
        ContentScreen(modifier = Modifier.padding(padding))
    }
}

@Composable
fun ContentScreen(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background) {
        Genre()
    }
}

@Preview(showBackground = true)
@Composable
fun CinematicsAppPreview() {
    CinematicsTheme {
        CinematicsApp()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES,
         showBackground = true)
@Composable
fun CinematicsAppPreviewDark() {
    CinematicsTheme {
        CinematicsApp()
    }
}
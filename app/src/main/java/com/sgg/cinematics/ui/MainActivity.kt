package com.sgg.cinematics.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.sgg.cinematics.ui.screen.CinematicsAppScreen
import com.sgg.cinematics.ui.ui.theme.CinematicsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val windowSizeClass = calculateWindowSizeClass(activity = this)
            val cinematicsAppState =
                rememberCinematicsAppState(windowWidthSizeClass = windowSizeClass.widthSizeClass)

            CinematicsTheme {
                CinematicsAppScreen(cinematicsAppState = cinematicsAppState)
            }

        }
    }
}

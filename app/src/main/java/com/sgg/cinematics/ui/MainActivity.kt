package com.sgg.cinematics.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.sgg.cinematics.ui.screen.CinematicsAppScreen
import com.sgg.cinematics.ui.ui.theme.CinematicsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CinematicsTheme {
                CinematicsAppScreen(viewModel)
            }
        }
    }
}

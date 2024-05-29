package com.sgg.cinematics.ui.commonui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sgg.cinematics.ui.ui.theme.CinematicsTheme
import com.sgg.cinematics.ui.ui.theme.md_theme_dark_onPrimary

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()) {
        CircularProgressIndicator(color = md_theme_dark_onPrimary)
    }
}

@Preview
@Composable
fun LoadingScreenPreview() {
    CinematicsTheme {
        LoadingScreen()
    }
}
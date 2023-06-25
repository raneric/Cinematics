package com.example.cinematics.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.cinematics.ui.ui.theme.CinematicsTheme

@Composable
fun Genre() {
    Text(text = "4.5")
}

@Preview(showBackground = true)
@Composable
fun GenrePreview() {
    CinematicsTheme {
        Genre()
    }
}
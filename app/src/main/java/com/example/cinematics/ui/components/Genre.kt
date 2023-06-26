package com.example.cinematics.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cinematics.R
import com.example.cinematics.ui.ui.theme.CinematicsTheme
import com.example.cinematics.ui.ui.theme.genreTypo
import com.example.cinematics.ui.ui.theme.md_theme_dark_onTertiaryContainer
import com.example.cinematics.ui.ui.theme.md_theme_light_onPrimary
import com.example.cinematics.ui.ui.theme.md_theme_light_tertiary
import com.example.cinematics.ui.ui.theme.md_theme_light_tertiaryContainer
import com.example.cinematics.ui.ui.theme.ratingTypo
import com.example.cinematics.ui.ui.theme.rating_positive

@Composable
fun Genre(text: String,
          color: Color = md_theme_light_onPrimary,
          modifier: Modifier = Modifier) {
    Text(text = text.uppercase(),
         color = color,
         style = genreTypo,
         modifier = modifier
                 .border(border = BorderStroke(2.dp, color),
                         shape = MaterialTheme.shapes.extraLarge)
                 .padding(horizontal = 8.dp, vertical = 5.dp))
}

@Preview(showBackground = true, backgroundColor = 0XFF000000)
@Composable
fun GenrePreview() {
    CinematicsTheme {
        Genre(text = "Action")
    }
}
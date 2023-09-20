package com.sgg.cinematics.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sgg.cinematics.ui.ui.theme.CinematicsTheme
import com.sgg.cinematics.ui.ui.theme.genreTypo
import com.sgg.cinematics.ui.ui.theme.md_theme_light_onPrimary

@Composable
fun GenreRow(genres: List<String>,
             color: Color = md_theme_light_onPrimary,
             compact: Boolean = false,
             modifier: Modifier = Modifier) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        genres.forEach {
            Genre(text = it, compact = compact, color = color)
        }
    }
}

@Composable
fun Genre(text: String,
          color: Color,
          compact: Boolean,
          modifier: Modifier = Modifier) {

    val textStyle = if (compact)
        genreTypo.copy(fontSize = 9.sp)
    else
        genreTypo

    Text(text = text.uppercase(),
         color = color,
         style = textStyle,
         modifier = modifier
                 .border(border = BorderStroke(2.dp, color),
                         shape = MaterialTheme.shapes.extraLarge)
                 .padding(horizontal = 8.dp, vertical = 5.dp))
}

@Preview(showBackground = true, backgroundColor = 0XFF000000)
@Composable
fun GenrePreview() {
    CinematicsTheme {
        Genre(text = "Action", compact = false, color = md_theme_light_onPrimary)
    }
}
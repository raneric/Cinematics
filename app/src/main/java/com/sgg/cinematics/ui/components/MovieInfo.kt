package com.sgg.cinematics.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sgg.cinematics.R
import com.sgg.cinematics.ui.ui.theme.md_theme_light_onPrimary

@Composable
fun MovieInfo(
    year: Int,
    duration: String,
    author: String,
    modifier: Modifier = Modifier,
    textColor: Color = md_theme_light_onPrimary,
    compact: Boolean = false
) {

    val textStyle = if (compact)
        MaterialTheme.typography.labelSmall
    else
        MaterialTheme.typography.labelMedium

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = modifier) {
        Text(text = year.toString(), color = textColor, style = textStyle)
        Text(text = duration, color = textColor, style = textStyle)
        Text(text = author, color = textColor, style = textStyle)
    }
}

@Composable
fun MovieInfoDetails(
    year: Int,
    duration: String,
    author: String,
    modifier: Modifier = Modifier,
    textColor: Color = MaterialTheme.colorScheme.onPrimary
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.label_movie_year, year.toString()),
            color = textColor,
            style = MaterialTheme.typography.labelMedium
        )
        Text(
            text = stringResource(id = R.string.label_movie_duration, duration),
            color = textColor,
            style = MaterialTheme.typography.labelMedium
        )
        Text(
            text = stringResource(id = R.string.label_movie_author, author),
            color = textColor,
            style = MaterialTheme.typography.labelMedium
        )
    }
}
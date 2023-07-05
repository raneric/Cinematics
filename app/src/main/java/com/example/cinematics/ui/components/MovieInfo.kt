package com.example.cinematics.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MovieInfo(year: Int,
              duration: String,
              author: String,
              textColor: Color = MaterialTheme.colorScheme.onPrimary,
              compact: Boolean = false,
              modifier: Modifier = Modifier) {

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
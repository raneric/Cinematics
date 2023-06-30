package com.example.cinematics.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Info(year: Int,
         duration: String,
         author: String,
         modifier: Modifier = Modifier) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier) {
        Text(text = year.toString(),
             color = MaterialTheme.colorScheme.onPrimary,
             style = MaterialTheme.typography.labelMedium)
        Text(text = duration,
             color = MaterialTheme.colorScheme.onPrimary,
             style = MaterialTheme.typography.labelMedium)
        Text(text = author,
             color = MaterialTheme.colorScheme.onPrimary,
             style = MaterialTheme.typography.labelMedium)
    }
}
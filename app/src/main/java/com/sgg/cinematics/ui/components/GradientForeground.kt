package com.sgg.cinematics.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * A gradient shade composable used as shade
 */
@Composable
fun GradientForeground(
    color: Color,
    modifier: Modifier = Modifier
) {
    val colorStop = arrayOf(
        0.0f to Color.Transparent,
        0.3f to color.copy(alpha = 0.3f),
        0.6f to color.copy(alpha = 0.5f),
        1f to color
    )
    val brush = Brush.verticalGradient(colorStops = colorStop)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(brush)
    ) {
    }
}
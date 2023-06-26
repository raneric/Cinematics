package com.example.cinematics.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cinematics.R
import com.example.cinematics.ui.ui.theme.CinematicsTheme

@Composable
fun MovieCad() {

}

@Composable
fun Poster(modifier: Modifier = Modifier) {
    Box(modifier = modifier
            .fillMaxWidth()
            .height(200.dp)) {
        Image(painter = painterResource(id = R.drawable.birds_of_prey),
              alignment = Alignment.Center,
              contentScale = ContentScale.Crop,
              contentDescription = "", modifier = Modifier.matchParentSize())
        BackDropGradient()
    }
}

@Composable
fun BackDropGradient() {
    val colorStop = arrayOf(
        0.0f to Color.Transparent,
        0.3f to Color.Black.copy(alpha = 0.3f),
        0.6f to Color.Black.copy(alpha = 0.5f),
        1f to Color.Black
    )
    val brush = Brush.verticalGradient(colorStops = colorStop)
    Box(modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(brush)) {
    }
}

@Preview
@Composable
fun PosterPreview() {
    CinematicsTheme {
        Poster()
    }
}
package com.example.cinematics.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cinematics.R
import com.example.cinematics.ui.ui.theme.CinematicsTheme

@Composable
fun MovieDetailsImage(@DrawableRes imageId: Int,
                      modifier: Modifier = Modifier) {
    Surface(shadowElevation = 8.dp,
            shape = MaterialTheme.shapes.small,
            modifier = modifier.size(width = 175.dp, height = 221.dp)) {
        Image(painter = painterResource(id = imageId),
              alignment = Alignment.Center,
              contentScale = ContentScale.Crop,
              contentDescription = "test",
              modifier = Modifier
                      .border(
                          width = 5.dp,
                          color = Color.White,
                          shape = MaterialTheme.shapes.small,
                      ))
    }
}

@Preview(showBackground = true)
@Composable
fun MovieDetailsImagePreview() {
    CinematicsTheme {
        MovieDetailsImage(R.drawable.birds_of_prey)
    }
}
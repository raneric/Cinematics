package com.sgg.cinematics.ui.commonui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.sgg.cinematics.ui.components.GradientForeground


@Composable
fun BackDrop(
    imageUrl: String,
    modifier: Modifier = Modifier
) {

    Box(modifier = modifier) {
        AsyncImage(
            model = imageUrl,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            contentDescription = ""
        )
        GradientForeground(color = Color.Black, modifier = Modifier.fillMaxHeight())
    }
}

@Preview
@Composable
fun BackDropPreview() {
    BackDrop(imageUrl = "https://firebasestorage.googleapis.com/v0/b/travelmantics-5312e.appspot.com/o/movies%2Favengers_end_game.jpg?alt=media&token=124db381-b61a-422d-806c-4b89a5871772")
}
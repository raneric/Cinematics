package com.sgg.cinematics.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.sgg.cinematics.ui.commonui.GradientForeground


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
            contentDescription = null
        )
        GradientForeground(color = Color.Black, modifier = Modifier.fillMaxHeight())
    }
}

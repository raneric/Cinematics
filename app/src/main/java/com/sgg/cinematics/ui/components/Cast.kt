package com.sgg.cinematics.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun Cast(
    userModels: List<String>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(userModels) {
            CastItem(imageUrl = it)
        }
    }
}

@Composable
fun CastItem(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    Image(
        painter = rememberAsyncImagePainter(model = imageUrl),
        contentDescription = "cast image",
        alignment = Alignment.Center,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(width = 70.dp, height = 70.dp)
            .clip(CircleShape)
    )
}

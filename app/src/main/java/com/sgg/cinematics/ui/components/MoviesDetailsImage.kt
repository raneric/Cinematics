package com.sgg.cinematics.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sgg.cinematics.R
import com.sgg.cinematics.ui.ui.theme.CinematicsTheme

val DETAILS_IMAGE_WIDTH = 175.dp
val DETAILS_IMAGE_HEIGHT = 221.dp

@Composable
fun MovieDetailsImage(@DrawableRes imageId: Int,
                      modifier: Modifier = Modifier) {
    Surface(shadowElevation = 8.dp,
            shape = MaterialTheme.shapes.small,
            modifier = modifier.size(width = DETAILS_IMAGE_WIDTH, height = DETAILS_IMAGE_HEIGHT)) {
        Image(painter = painterResource(id = imageId),
              alignment = Alignment.Center,
              contentScale = ContentScale.Crop,
              contentDescription = stringResource(id = R.string.content_descrip_movie_image),
              modifier = Modifier
                      .border(
                          width = 5.dp,
                          color = MaterialTheme.colorScheme.surface,
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
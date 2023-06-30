package com.example.cinematics.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cinematics.R
import com.example.cinematics.data.User
import com.example.cinematics.ui.ui.theme.CinematicsTheme

@Composable
fun Cast(users: List<User>,
         modifier: Modifier = Modifier) {
    Row {
        users.forEach {
            CastItem(imageId = it.picture)
        }
    }

}

@Composable
fun CastItem(@DrawableRes imageId: Int,
             modifier: Modifier = Modifier) {
    Image(painter = painterResource(id = imageId),
          contentDescription = "cast image",
          alignment = Alignment.Center,
          contentScale = ContentScale.Crop,
          modifier = Modifier
                  .size(width = 70.dp, height = 70.dp)
                  .clip(CircleShape))
}


@Preview
@Composable
fun CastItemPreview() {
    CinematicsTheme {
        CastItem(imageId = R.drawable.birds_of_prey)
    }
}
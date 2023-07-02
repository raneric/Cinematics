package com.example.cinematics.ui.components

import androidx.annotation.DrawableRes
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cinematics.data.User
import com.example.cinematics.data.userList
import com.example.cinematics.ui.ui.theme.CinematicsTheme

@Composable
fun Cast(users: List<User>,
         modifier: Modifier = Modifier) {
    LazyRow(modifier = modifier,
            contentPadding = PaddingValues(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp)) {
        items(users) {
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
          modifier = modifier
                  .size(width = 70.dp, height = 70.dp)
                  .clip(CircleShape))
}


@Preview(showBackground = true)
@Composable
fun CastItemPreview() {
    CinematicsTheme {
        CastItem(userList[0].picture)
    }
}

@Preview(showBackground = true)
@Composable
fun CastPreview() {
    CinematicsTheme {
        Cast(userList)
    }
}
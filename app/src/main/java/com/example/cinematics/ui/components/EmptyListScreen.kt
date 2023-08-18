package com.example.cinematics.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cinematics.R
import com.example.cinematics.ui.commonui.BackDrop
import com.example.cinematics.ui.ui.theme.CinematicsTheme

@Composable
fun EmptyListScreen(modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.Center) {
        BackDrop(imageId = R.drawable.background)
        Surface(modifier = modifier
                .clip(MaterialTheme.shapes.large),
                color = Color.White.copy(alpha = 0.4f)) {
            Column(verticalArrangement = Arrangement.Center,
                   horizontalAlignment = Alignment.CenterHorizontally) {
                Image(modifier = Modifier.size(250.dp),
                      painter = painterResource(id = R.drawable.empty_folder),
                      contentDescription = "empty list")
                Text(
                    text = stringResource(id = R.string.txt_no_item_found),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface)
            }
        }
    }
}

@Preview
@Composable
fun EmptyListScreenPreview() {
    CinematicsTheme {
        EmptyListScreen()
    }
}
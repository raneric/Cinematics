package com.example.cinematics.ui.commonui

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.cinematics.ui.components.GradientForeground


@Composable
fun BackDrop(@DrawableRes imageId: Int,
             modifier: Modifier = Modifier) {

    Box(modifier = modifier) {
        Image(painter = painterResource(id = imageId),
              contentScale = ContentScale.Crop,
              modifier = Modifier.fillMaxSize(),
              contentDescription = "")
        GradientForeground(color = Color.Black, modifier = Modifier.fillMaxHeight())
    }
}
package com.sgg.cinematics.ui.commonui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sgg.cinematics.ui.ui.theme.CinematicsTheme
import com.sgg.cinematics.utils.DarkAndLightPreview

@Composable
fun BackNavigationTopBar(
        modifier: Modifier = Modifier,
        onNavigateBack: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 8.dp)) {
        IconButton(onClick = onNavigateBack) {
            Icon(painter = painterResource(id = com.google.android.material.R.drawable.ic_arrow_back_black_24),
                 contentDescription = "")
        }
    }
}

@DarkAndLightPreview
@Composable
private fun BackNavigationTopBarPreview() {
    CinematicsTheme {
        BackNavigationTopBar() {

        }
    }
}
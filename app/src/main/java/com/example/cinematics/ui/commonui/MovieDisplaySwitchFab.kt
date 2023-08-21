package com.example.cinematics.ui.commonui

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import com.example.cinematics.R
import com.example.cinematics.ui.ui.theme.CinematicsTheme

@Composable
fun MovieDisplaySwitchFab(@DrawableRes fabIcon: Int,
                          modifier: Modifier = Modifier,
                          onViewSwitched: () -> Unit) {
    FloatingActionButton(onClick = onViewSwitched) {
        Icon(painter = painterResource(id = fabIcon),
             tint = MaterialTheme.colorScheme.onPrimaryContainer,
             contentDescription = stringResource(id = R.string.content_descript_fab_view_switch),
             modifier = modifier.testTag(fabIcon.toString()))
    }
}

@Preview
@Composable
fun MovieDisplaySwitchFabPreview() {
    CinematicsTheme {
        MovieDisplaySwitchFab(fabIcon = R.drawable.view_list_32) {

        }
    }
}
package com.sgg.cinematics.ui.commonui

import androidx.annotation.DrawableRes
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.sgg.cinematics.R
import com.sgg.cinematics.ui.ui.theme.CinematicsTheme

@Composable
fun MovieDisplaySwitchFab(@DrawableRes fabIcon: Int,
                          modifier: Modifier = Modifier.testTag(stringResource(id = R.string.test_tag_fab_view_switch)),
                          onViewSwitched: () -> Unit) {
    FloatingActionButton(modifier = modifier, onClick = onViewSwitched) {
        Icon(painter = painterResource(id = fabIcon),
             tint = MaterialTheme.colorScheme.onPrimaryContainer,
             contentDescription = stringResource(id = R.string.content_descript_fab_view_switch),
             modifier = Modifier.testTag(fabIcon.toString()))
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
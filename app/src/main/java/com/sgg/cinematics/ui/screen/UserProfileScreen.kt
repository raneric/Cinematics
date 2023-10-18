package com.sgg.cinematics.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sgg.cinematics.R
import com.sgg.cinematics.ui.ui.theme.CinematicsTheme
import com.sgg.cinematics.ui.ui.theme.ProfileBackgroundShape
import com.sgg.cinematics.ui.ui.theme.ShapeCutPosition
import com.sgg.cinematics.ui.ui.theme.profil_background_color
import com.sgg.cinematics.ui.ui.theme.watched_btn_color

@Composable
fun UserProfileScreen(modifier: Modifier = Modifier) {
    val fabSize = with(LocalDensity.current) { 60.dp.toPx() }
    UserProfileLayout(modifier = modifier.padding(end = 28.dp), pictureSection = {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
               verticalArrangement = Arrangement.SpaceAround,
               modifier = Modifier
                       .fillMaxWidth()
                       .height(275.dp)
                       .clip(ProfileBackgroundShape(fabSize, ShapeCutPosition.BOTTOM_RIGHT))
                       .background(profil_background_color)) {
            Image(
                painter = painterResource(id = R.drawable.profil1),
                contentDescription = "",
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape),
            )
            Text(text = "RANRIANARISOA Eric Eugène", style = MaterialTheme.typography.titleMedium)
        }
    }, fab = {
        FloatingActionButton(onClick = {},
                             elevation = FloatingActionButtonDefaults.elevation(12.dp),
                             containerColor = watched_btn_color,
                             contentColor = MaterialTheme.colorScheme.onPrimary,
                             shape = CircleShape) {
            Icon(painter = painterResource(id = R.drawable.icon_edit_32),
                 contentDescription = stringResource(id = R.string.content_descrip_back_fab))
        }
    }, userInfo = {

    })
}

@Composable
fun UserProfileLayout(pictureSection: @Composable () -> Unit,
                      fab: @Composable () -> Unit,
                      userInfo: @Composable () -> Unit,
                      modifier: Modifier = Modifier) {

    Layout(contents = listOf(pictureSection, fab, userInfo),
           modifier = modifier) { (pictureSectionMeasurable, fabMeasurable, userInfoMeasurable), constrait ->

        val internalXPadding = 28.dp.toPx()
        val internalYPadding = 32.dp.toPx()
        val sectionMargin = 24.dp.toPx()

        val picturePlaceable = pictureSectionMeasurable.first()
                .measure(constrait)
        val fabPlaceable = fabMeasurable.first()
                .measure(constrait)
        val userInfoPlaceable = userInfoMeasurable.first()
                .measure(constrait)

        val fabOverflow = fabPlaceable.width / 5

        val userInfoY = (picturePlaceable.height + sectionMargin + internalYPadding).toInt()

        val contentX = (internalXPadding + fabOverflow).toInt()

        val layoutWidth = picturePlaceable.width + fabOverflow + contentX
        val layoutHeight = (picturePlaceable.height + sectionMargin + userInfoPlaceable.height).toInt()

        val fabY = internalYPadding.toInt() + picturePlaceable.height + (sectionMargin / 2).toInt() - (fabPlaceable.height / 2)
        val fabX = contentX + picturePlaceable.width - fabPlaceable.width + fabOverflow

        layout(width = layoutWidth, height = layoutHeight) {
            picturePlaceable.place(x = contentX, y = internalYPadding.toInt())
            fabPlaceable.place(x = fabX, y = fabY, zIndex = 2f)
            userInfoPlaceable.place(x = contentX, y = userInfoY)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserProfileScreenPreview() {
    CinematicsTheme {
        UserProfileScreen()
    }
}
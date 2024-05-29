package com.sgg.cinematics.ui.commonui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sgg.cinematics.R
import com.sgg.cinematics.ui.ui.theme.CinematicsTheme
import com.sgg.cinematics.ui.ui.theme.customButtonTextStyle
import com.sgg.cinematics.ui.ui.theme.custom_green_btn_color
import com.sgg.cinematics.ui.ui.theme.custom_red_button_color

@Composable
fun AddOrRemoveToWatchListButton(
        modifier: Modifier = Modifier,
        inWatchList: Boolean = false,
        onClick: () -> Unit
) {

    val animatedColor by animateColorAsState(
            if (inWatchList) custom_green_btn_color else custom_red_button_color,
            label = "custom_button_color",
            animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearEasing,
            )

    )

    val buttonColor = ButtonDefaults.buttonColors(
            containerColor = animatedColor,
            contentColor = Color.White
    )

    val iconId = if (inWatchList) R.drawable.icon_delete_24px else R.drawable.icon_watch_list_24

    val stringId = if (inWatchList) R.string.txt_remove_to_watch_btn else R.string.txt_add_to_watch_btn

    Button(
            onClick = onClick,
            shape = MaterialTheme.shapes.small,
            colors = buttonColor,
            modifier = modifier
                .fillMaxWidth()
                .height(50.dp)
    ) {
        Row {
            Icon(
                    painter = painterResource(id = iconId),
                    contentDescription = ""
            )
            Text(
                    text = stringResource(id = stringId),
                    style = customButtonTextStyle
            )
        }
    }
}

@Composable
fun CameraButton(
        modifier: Modifier = Modifier,
        onButtonClick: () -> Unit
) {
    IconButton(modifier = modifier,
               onClick = { onButtonClick() }) {
        Icon(modifier = Modifier.size(32.dp),
             painter = painterResource(id = R.drawable.icon_photo_camera_24px),
             tint = MaterialTheme.colorScheme.primary,
             contentDescription = ""
        )
    }
}

@Preview
@Composable
fun CustomButtonPreview() {
    CinematicsTheme {
        AddOrRemoveToWatchListButton(inWatchList = false) {

        }
    }
}
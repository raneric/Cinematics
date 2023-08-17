package com.example.cinematics.ui.commonui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.cinematics.R
import com.example.cinematics.ui.ui.theme.add_to_watch_button_color
import com.example.cinematics.ui.ui.theme.customButtonTextStyle
import com.example.cinematics.ui.ui.theme.watched_btn_color

@Composable
fun CustomButton(modifier: Modifier = Modifier,
                 inWatchList: Boolean = false,
                 onClick: () -> Unit) {

    val animatedColor by animateColorAsState(
        if (inWatchList) watched_btn_color else add_to_watch_button_color,
        label = "custom_button_color",
        animationSpec = tween(
            durationMillis = 300,
            easing = LinearEasing,
        )

    )

    val buttonColor = ButtonDefaults.buttonColors(containerColor = animatedColor,
                                                  contentColor = Color.White)

    val iconId = if (inWatchList) R.drawable.icon_watched_24 else R.drawable.icon_watch_list_24

    val stringId = if (inWatchList) R.string.txt_remove_to_watch_btn else R.string.txt_add_to_watch_btn

    Button(onClick = onClick,
           shape = MaterialTheme.shapes.small,
           colors = buttonColor,
           modifier = modifier
                   .fillMaxWidth()
                   .height(50.dp)) {
        Row {
            Icon(painter = painterResource(id = iconId),
                 contentDescription = "")
            Text(text = stringResource(id = stringId),
                 style = customButtonTextStyle)
        }
    }
}

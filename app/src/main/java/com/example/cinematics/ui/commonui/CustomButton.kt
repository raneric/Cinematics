package com.example.cinematics.ui.commonui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.cinematics.ui.ui.theme.customButtonTextStyle

@Composable
fun CustomButton(modifier: Modifier = Modifier,
                 color: ButtonColors,
                 iconId: Int,
                 stringId: Int,
                 onClick: () -> Unit) {
    Button(onClick = onClick,
           shape = MaterialTheme.shapes.small,
           colors = color,
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

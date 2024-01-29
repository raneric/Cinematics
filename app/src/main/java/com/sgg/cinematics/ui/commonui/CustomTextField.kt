package com.sgg.cinematics.ui.commonui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.sgg.cinematics.R


@Composable
fun PasswordTextField(
        modifier: Modifier = Modifier,
        value: String?,
        onPasswordChange: (String) -> Unit
) {
    TextField(
        value = value ?: "",
        onValueChange = { password ->
            onPasswordChange(password)
        },
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyLarge,
                text = stringResource(id = R.string.placeholder_password))
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.icon_lock_24px),
                contentDescription = stringResource(id = R.string.content_descrip_password_icon))
        },
        visualTransformation = PasswordVisualTransformation(),
    )
}

@Composable
fun ControlledTextField(
        modifier: Modifier = Modifier,
        value: String?,
        @DrawableRes iconResId: Int,
        @StringRes placeholderResId: Int,
        @StringRes iconContentDescripResId: Int,
        isValidData: (String) -> Boolean,
        onValueChange: (String) -> Unit
) {
    val isValidData = if (value != null) isValidData(value) else true
    TextField(value = value ?: "",
              isError = !isValidData,
              onValueChange = { email ->
                  onValueChange(email)
              }, placeholder = {
            Text(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyLarge,
                text = stringResource(id = placeholderResId))
        }, leadingIcon = {
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = stringResource(id = iconContentDescripResId))
        }, modifier = Modifier.fillMaxWidth())
}

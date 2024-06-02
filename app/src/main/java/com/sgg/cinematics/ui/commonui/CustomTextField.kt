package com.sgg.cinematics.ui.commonui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.sgg.cinematics.R

@Composable
fun PasswordTextFieldWrapper(
    value: String?,
    isError: Boolean,
    placeHolder: String,
    onPasswordChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textFieldVariant: TextFieldVariant = TextFieldVariant.OUTLINED,
    errorMessage: String = "",
) {

    var passwordVisualTransform: VisualTransformation by remember {
        mutableStateOf(PasswordVisualTransformation())
    }

    val passwordTrailingIcon = if (passwordVisualTransform == PasswordVisualTransformation()) {
        painterResource(id = R.drawable.icon_visibility_lock_24px)
    } else {
        painterResource(id = R.drawable.icon_visibility_off_24px)
    }

    when (textFieldVariant) {
        TextFieldVariant.FILLED   -> {
            TextField(
                value = value ?: "",
                isError = isError,
                onValueChange = { password ->
                    onPasswordChange(password)
                },
                modifier = modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodyLarge,
                        text = placeHolder
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_lock_24px),
                        contentDescription = stringResource(id = R.string.content_descrip_password_icon)
                    )
                },
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisualTransform = reversePasswordState(
                            passwordVisualTransform
                        )
                    }) {
                        Icon(
                            painter = passwordTrailingIcon,
                            contentDescription = stringResource(id = R.string.content_descrip_password_icon)
                        )
                    }

                },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(
                        alpha = 0.6f
                    )
                ),
                supportingText = {
                    if (isError) {
                        Text(text = errorMessage)
                    }
                },
                visualTransformation = passwordVisualTransform,
            )
        }

        TextFieldVariant.OUTLINED -> {
            OutlinedTextField(
                value = value ?: "",
                isError = isError,
                onValueChange = { password ->
                    onPasswordChange(password)
                },
                modifier = modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodyLarge,
                        text = placeHolder
                    )
                },
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisualTransform = reversePasswordState(
                            passwordVisualTransform
                        )
                    }) {
                        Icon(
                            painter = passwordTrailingIcon,
                            contentDescription = stringResource(id = R.string.content_descrip_password_icon)
                        )
                    }

                },
                supportingText = {
                    if (isError) {
                        Text(text = errorMessage)
                    }
                },
                visualTransformation = passwordVisualTransform,
            )
        }
    }
}

@Composable
fun ControlledTextField(
    value: String?,
    @DrawableRes iconResId: Int,
    @StringRes placeholderResId: Int,
    @StringRes iconContentDescripResId: Int,
    isValidData: (String) -> Boolean,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isValidData = if (value != null) isValidData(value) else true

    TextField(
        value = value ?: "",
        isError = !isValidData,
        onValueChange = { email ->
            onValueChange(email)
        },
        placeholder = {
            Text(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyLarge,
                text = stringResource(id = placeholderResId)
            )
        }, colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
        ), leadingIcon = {
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = stringResource(id = iconContentDescripResId)
            )
        }, modifier = modifier.fillMaxWidth()
    )
}

@Composable
fun ControlledOutlinedTextField(
    value: String?,
    @DrawableRes iconResId: Int,
    @StringRes placeholderResId: Int,
    @StringRes iconContentDescripResId: Int,
    isValidData: (String) -> Boolean,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isValidData = if (value != null) isValidData(value) else true

    OutlinedTextField(
        value = value ?: "",
        isError = !isValidData,
        onValueChange = { email ->
            onValueChange(email)
        },
        placeholder = {
            Text(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyLarge,
                text = stringResource(id = placeholderResId)
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = stringResource(id = iconContentDescripResId)
            )
        }, modifier = modifier.fillMaxWidth()
    )
}

private fun reversePasswordState(visualTransformation: VisualTransformation): VisualTransformation {
    return if (visualTransformation == VisualTransformation.None) {
        PasswordVisualTransformation()
    } else {
        VisualTransformation.None
    }
}

enum class TextFieldVariant {
    OUTLINED, FILLED
}
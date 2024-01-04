package com.sgg.cinematics.ui.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sgg.cinematics.R
import com.sgg.cinematics.ui.ui.theme.CinematicsTheme
import com.sgg.cinematics.ui.ui.theme.custom_red_button_color
import com.sgg.cinematics.ui.ui.theme.md_theme_light_onSecondary
import com.sgg.cinematics.ui.ui.theme.md_theme_light_secondary
import com.sgg.cinematics.utils.DarkAndLightPreview

@Composable
fun LoginScreen(
        modifier: Modifier = Modifier,
) {
    val viewModel = hiltViewModel<LoginViewModel>()
    val userData = viewModel.uiDate.collectAsStateWithLifecycle()
    val isEmailValid = viewModel.isEmailValid.collectAsStateWithLifecycle()
    Column(
        modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        EmailTextField(value = userData.value.user?.email,
                       isEmailValid = isEmailValid.value,
                       onEmailChange = { viewModel.updateEmail(it) })
        Spacer(modifier = Modifier.height(16.dp))

        PasswordTextField(
            value = userData.value.user?.password,
            onPasswordChange = { viewModel.updatePassword(it) })
        Spacer(modifier = Modifier.height(16.dp))

        SinInSignUpButton(onLoginClick = {}, onCreateAccountClick = {})
    }
}

@Composable
fun EmailTextField(
        modifier: Modifier = Modifier,
        value: String?,
        isEmailValid: Boolean,
        onEmailChange: (String) -> Unit
) {
    TextField(value = value ?: "",
              isError = !isEmailValid,
              onValueChange = { email ->
                  onEmailChange(email)
              }, placeholder = {
            Text(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyLarge,
                text = stringResource(id = R.string.placeholder_mail))
        }, leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.icon_mail_24px),
                contentDescription = stringResource(id = R.string.content_descrip_mail_icon))
        }, modifier = Modifier.fillMaxWidth())
}

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
fun SinInSignUpButton(
        onLoginClick: () -> Unit,
        onCreateAccountClick: () -> Unit
) {
    Button(
        onClick = { onLoginClick },
        colors = ButtonDefaults.buttonColors(md_theme_light_secondary),
        shape = MaterialTheme.shapes.small,
        modifier = Modifier.fillMaxWidth()) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(
                tint = md_theme_light_onSecondary,
                painter = painterResource(id = R.drawable.icon_login_24px),
                contentDescription = "")
            Text(
                color = md_theme_light_onSecondary,
                style = MaterialTheme.typography.labelLarge,
                text = stringResource(id = R.string.txt_login_btn))
        }
    }

    Button(
        onClick = { onLoginClick },
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(custom_red_button_color),
        modifier = Modifier.fillMaxWidth()) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.icon_gmail),
                modifier = Modifier.size(24.dp),
                contentDescription = "")
            Text(
                color = md_theme_light_onSecondary,
                style = MaterialTheme.typography.labelLarge,
                text = stringResource(id = R.string.txt_login_with_google_btn))
        }
    }

    TextButton(onClick = { onCreateAccountClick }) {
        Text(
            style = MaterialTheme.typography.labelLarge, text = "Create account")
    }
}

@DarkAndLightPreview
@Composable
fun LoginScreenPreview() {
    CinematicsTheme {
        LoginScreen()
    }
}

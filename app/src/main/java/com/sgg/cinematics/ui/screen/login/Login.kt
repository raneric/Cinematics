package com.sgg.cinematics.ui.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sgg.cinematics.R
import com.sgg.cinematics.data.model.AuthData
import com.sgg.cinematics.ui.commonui.BackNavigationFab
import com.sgg.cinematics.ui.commonui.ControlledTextField
import com.sgg.cinematics.ui.commonui.PasswordTextField
import com.sgg.cinematics.ui.ui.theme.CinematicsTheme
import com.sgg.cinematics.ui.ui.theme.custom_red_button_color
import com.sgg.cinematics.ui.ui.theme.md_theme_light_onSecondary
import com.sgg.cinematics.ui.ui.theme.md_theme_light_secondary
import com.sgg.cinematics.utils.DarkAndLightPreview

@Composable
fun LoginScreen(
        modifier: Modifier = Modifier,
        userData: AuthData?,
        isEmailValid: (String) -> Boolean,
        updateEmail: (String) -> Unit,
        updatePassword: (String) -> Unit,
        login: () -> Unit,
        onNavigateBack: () -> Unit,
        onCreateAccountClick: () -> Unit
) {
    Box {
        BackNavigationFab(onNavigateBack = onNavigateBack)
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            ControlledTextField(value = userData?.email,
                                iconResId = R.drawable.icon_mail_24px,
                                placeholderResId = R.string.placeholder_mail,
                                iconContentDescripResId = R.string.content_descrip_mail_icon,
                                isValidData = { email -> isEmailValid(email) },
                                onValueChange = { email -> updateEmail(email) })
            Spacer(modifier = Modifier.height(16.dp))

            PasswordTextField(value = userData?.password, onPasswordChange = { updatePassword(it) })
            Spacer(modifier = Modifier.height(16.dp))

            SinInSignUpButton(
                isLoginButtonEnabled = (userData?.password != ""),
                onLoginClick = { login() },
                onCreateAccountClick = onCreateAccountClick)
        }
    }
}

@Composable
fun SinInSignUpButton(
        isLoginButtonEnabled: Boolean,
        onLoginClick: () -> Unit,
        onCreateAccountClick: () -> Unit
) {
    Button(
        enabled = isLoginButtonEnabled,
        onClick = onLoginClick,
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
        onClick = { },
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

    TextButton(onClick = onCreateAccountClick) {
        Text(
            style = MaterialTheme.typography.labelLarge,
            text = stringResource(id = R.string.txt_create_account))
    }
}

@DarkAndLightPreview
@Composable
fun LoginScreenPreview() {
    CinematicsTheme {
        LoginScreen(userData = null,
                    isEmailValid = { true },
                    updateEmail = {},
                    updatePassword = {},
                    login = {},
                    onNavigateBack = {},
                    onCreateAccountClick = {})
    }
}

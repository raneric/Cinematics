package com.sgg.cinematics.ui.screen.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sgg.cinematics.R
import com.sgg.cinematics.ui.ui.theme.CinematicsTheme

@Composable
fun LoginScreen(
        modifier: Modifier = Modifier,
        onEmailChange: (String) -> Unit,
        onPasswordChange: (String) -> Unit
) {
    Column(
        modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        EmailTextField(onEmailChange = onEmailChange)
        Spacer(modifier = Modifier.height(16.dp))

        PasswordTextField(onPasswordChange = onPasswordChange)
        Spacer(modifier = Modifier.height(16.dp))

        SinInSignUpButton(onLoginClick = {}, onCreateAccountClick = {})
    }
}


@Composable
fun EmailTextField(
        modifier: Modifier = Modifier,
        onEmailChange: (String) -> Unit
) {
    TextField(
        value = "",
        onValueChange = { email ->
            onEmailChange(email)
        },
        placeholder = {
            Text(
                style = MaterialTheme.typography.labelLarge,
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
        onPasswordChange: (String) -> Unit
) {
    TextField(
        value = "",
        onValueChange = { password ->
            onPasswordChange(password)
        },
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(
                style = MaterialTheme.typography.labelLarge,
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
        shape = MaterialTheme.shapes.small,
        modifier = Modifier.fillMaxWidth()) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.icon_login_24px),
                contentDescription = "")
            Text(
                style = MaterialTheme.typography.labelLarge,
                text = stringResource(id = R.string.txt_login_btn))
        }
    }
    TextButton(onClick = { onCreateAccountClick }) {
        Text(
            style = MaterialTheme.typography.labelLarge, text = "Create account")
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    CinematicsTheme {
        LoginScreen(onEmailChange = {}, onPasswordChange = {})
    }
}
package com.sgg.cinematics.ui.screen.account

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sgg.cinematics.R
import com.sgg.cinematics.data.model.AuthData
import com.sgg.cinematics.data.model.UserModel
import com.sgg.cinematics.ui.commonui.BackNavigationFab
import com.sgg.cinematics.ui.commonui.ControlledOutlinedTextField
import com.sgg.cinematics.ui.commonui.CustomDropdownMenu
import com.sgg.cinematics.ui.ui.theme.CinematicsTheme
import com.sgg.cinematics.ui.ui.theme.md_theme_light_onSecondary
import com.sgg.cinematics.ui.ui.theme.md_theme_light_secondary
import com.sgg.cinematics.utils.DarkAndLightPreview
import com.sgg.cinematics.utils.validateEmail
import java.time.Month
import java.time.Year

@Composable
fun CreateAccountScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    onCreateAccountClick: () -> Unit
) {

    val scrollState = rememberScrollState()

    val viewModel = hiltViewModel<UserProfileViewModel>()

    val user = viewModel.userInfo

    val authData = viewModel.authData

    Box {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp),
               horizontalAlignment = Alignment.CenterHorizontally,
               modifier = modifier
                   .fillMaxSize()
                   .padding(16.dp)
                   .verticalScroll(scrollState)
        ) {
            ProfilePicture()
            UserFullName(userInfo = user) { user ->
                viewModel.updateUserInfo(user)
            }
            UserEmail(
                    userInfo = user,
                    emailValidation = {
                        validateEmail(it)
                    },
                    onValueChange = { user ->
                        viewModel.updateUserInfo(user)
                        viewModel.updateAuthData(authData.copy(email = user.email ?: ""))
                    })
            PasswordInputs(authData = authData) { authData ->
                viewModel.updateAuthData(authData)
            }
            BirthDatePicker()
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(tint = MaterialTheme.colorScheme.onSurface,
                     painter = painterResource(id = R.drawable.icon_men_24px),
                     contentDescription = ""
                )
                CustomDropdownMenu(modifier = textFiledModifier,
                                   selectedValue = "",
                                   optionList = stringArrayResource(id = R.array.gender_list).toList(),
                                   textLabel = stringResource(id = R.string.label_gender),
                                   onValueChange = {})
            }
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(tint = MaterialTheme.colorScheme.onSurface,
                     painter = painterResource(id = R.drawable.icon_home_pin_24px),
                     contentDescription = ""
                )
                OutlinedTextField(value = "", modifier = textFiledModifier, label = {
                    Text(text = stringResource(id = R.string.label_location),
                         style = MaterialTheme.typography.bodySmall
                    )
                }, onValueChange = {})
            }
            Row {
                Spacer(modifier = Modifier.size(32.dp))
                Button(onClick = onCreateAccountClick,
                       colors = ButtonDefaults.buttonColors(md_theme_light_secondary),
                       shape = MaterialTheme.shapes.small,
                       modifier = Modifier.fillMaxWidth()
                ) {
                    Text(color = md_theme_light_onSecondary,
                         style = MaterialTheme.typography.labelLarge,
                         text = stringResource(id = R.string.txt_create_account_button)
                    )
                }
            }
        }
        BackNavigationFab(onNavigateBack = onNavigateBack)
    }
}

@Composable
fun ProfilePicture(
    modifier: Modifier = Modifier,
    picture: Painter = painterResource(id = R.drawable.default_user_profile)
) {
    Box(modifier = modifier.padding(8.dp)) {
        Image(modifier = Modifier
            .size(200.dp)
            .clip(CircleShape)
            .border(BorderStroke(4.dp, MaterialTheme.colorScheme.outline), CircleShape)
            .padding(4.dp)
            .align(alignment = Alignment.Center),
              painter = picture,
              contentScale = ContentScale.Crop,
              contentDescription = ""
        )
        IconButton(modifier = Modifier.align(alignment = Alignment.BottomEnd),
                   onClick = { /*TODO*/ }) {
            Icon(modifier = Modifier.size(32.dp),
                 painter = painterResource(id = R.drawable.icon_photo_camera_24px),
                 tint = MaterialTheme.colorScheme.primary,
                 contentDescription = ""
            )
        }
    }
}

@Composable
fun UserFullName(
    modifier: Modifier = Modifier,
    userInfo: UserModel,
    onValueChange: (UserModel) -> Unit,
) {
    Row(modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(tint = MaterialTheme.colorScheme.onSurface,
             painter = painterResource(id = R.drawable.icon_profile_24),
             contentDescription = ""
        )
        Column {
            OutlinedTextField(value = userInfo.firstName,
                              modifier = textFiledModifier,
                              label = {
                                  Text(text = stringResource(id = R.string.label_first_name),
                                       style = MaterialTheme.typography.bodySmall
                                  )
                              },
                              onValueChange = { firstName ->
                                  onValueChange(userInfo.copy(lastName = firstName))
                              })
            OutlinedTextField(value = userInfo.lastName,
                              modifier = textFiledModifier,
                              label = {
                                  Text(text = stringResource(id = R.string.label_last_name),
                                       style = MaterialTheme.typography.bodySmall
                                  )
                              },
                              onValueChange = { lastName ->
                                  onValueChange(userInfo.copy(lastName = lastName))
                              })
        }
    }
}

@Composable
fun UserEmail(
    modifier: Modifier = Modifier,
    userInfo: UserModel,
    emailValidation: (String) -> Boolean,
    onValueChange: (UserModel) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(tint = MaterialTheme.colorScheme.onSurface,
             painter = painterResource(id = R.drawable.icon_mail_24px),
             contentDescription = ""
        )
        ControlledOutlinedTextField(value = userInfo.email,
                                    iconResId = R.drawable.icon_mail_24px,
                                    placeholderResId = R.string.placeholder_mail,
                                    iconContentDescripResId = R.string.content_descrip_mail_icon,
                                    isValidData = { emailValidation(it) },
                                    onValueChange = { email ->
                                        onValueChange(userInfo.copy(email = email))
                                    })
    }
}

@Composable
fun PasswordInputs(
    modifier: Modifier = Modifier,
    authData: AuthData,
    onValueChange: (AuthData) -> Unit,
) {

    var passwordVisualTransform: VisualTransformation by remember {
        mutableStateOf(PasswordVisualTransformation())
    }

    val passwordTrailingIcon = if (passwordVisualTransform == PasswordVisualTransformation()) {
        painterResource(id = R.drawable.icon_visibility_lock_24px)
    } else {
        painterResource(id = R.drawable.icon_visibility_off_24px)
    }

    val passwordConfirmation = remember {
        mutableStateOf("")
    }

    val isPasswordDifferent = remember {
        mutableStateOf(false)
    }

    Row(modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(tint = MaterialTheme.colorScheme.onSurface,
             painter = painterResource(id = R.drawable.icon_key_24px),
             contentDescription = ""
        )
        Column {
            OutlinedTextField(value = authData.password,
                              modifier = textFiledModifier,
                              isError = isPasswordDifferent.value,
                              label = {
                                  Text(text = stringResource(id = R.string.label_password),
                                       style = MaterialTheme.typography.bodySmall
                                  )
                              },
                              onValueChange = { password ->
                                  onValueChange(authData.copy(password = password))
                                  isPasswordDifferent.value = password != passwordConfirmation.value
                              },
                              trailingIcon = {
                                  IconButton(onClick = {
                                      passwordVisualTransform = reversePasswordState(
                                              passwordVisualTransform
                                      )
                                  }) {
                                      Icon(painter = passwordTrailingIcon,
                                           contentDescription = ""
                                      )
                                  }
                              },
                              visualTransformation = passwordVisualTransform
            )
            OutlinedTextField(value = passwordConfirmation.value,
                              isError = isPasswordDifferent.value,
                              modifier = textFiledModifier,
                              label = {
                                  Text(text = stringResource(id = R.string.label_confirm_password),
                                       style = MaterialTheme.typography.bodySmall
                                  )
                              },
                              onValueChange = { passwordConfirm ->
                                  passwordConfirmation.value = passwordConfirm
                                  isPasswordDifferent.value = passwordConfirm != authData.password
                              },
                              trailingIcon = {
                                  IconButton(onClick = {
                                      passwordVisualTransform = reversePasswordState(
                                              passwordVisualTransform
                                      )
                                  }) {
                                      Icon(painter = passwordTrailingIcon,
                                           contentDescription = ""
                                      )
                                  }
                              },
                              visualTransformation = passwordVisualTransform
            )
        }
    }
}

@Composable
fun BirthDatePicker(modifier: Modifier = Modifier) {

    var selectedValue by remember {
        mutableStateOf("0")
    }

    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(tint = MaterialTheme.colorScheme.onSurface,
             painter = painterResource(id = R.drawable.icon_calendar_24px),
             contentDescription = ""
        )
        CustomDropdownMenu(modifier = Modifier.weight(0.5f),
                           selectedValue = selectedValue,
                           optionList = DAY_OPTION_LIST,
                           textLabel = stringResource(id = R.string.label_day_of_birth),
                           onValueChange = {
                               selectedValue = it
                           })
        CustomDropdownMenu(modifier = Modifier.weight(0.5f),
                           selectedValue = selectedValue,
                           optionList = MONTH_LIST,
                           textLabel = stringResource(id = R.string.label_month_of_birth),
                           onValueChange = {
                               selectedValue = it
                           })
        CustomDropdownMenu(modifier = Modifier.weight(1f),
                           selectedValue = selectedValue,
                           optionList = YEAR_RANGE,
                           textLabel = stringResource(id = R.string.label_year_of_birth),
                           onValueChange = {
                               selectedValue = it
                           })
    }
}

@DarkAndLightPreview
@Composable
fun CreateAccountScreenPreview() {
    CinematicsTheme {
        CreateAccountScreen(
                onNavigateBack = {},
                onCreateAccountClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePicturePreview() {
    CinematicsTheme {
        ProfilePicture()
    }
}

val DAY_OPTION_LIST = (1..31).map { it.toString() }

val MONTH_LIST = Month.values()
    .map { month ->
        month.name.lowercase()
            .replaceFirstChar { it.uppercase() }
    }

val YEAR_RANGE = (1905..Year.now().value).sortedDescending()
    .map { it.toString() }

val textFiledModifier = Modifier.fillMaxWidth()

private fun reversePasswordState(visualTransformation: VisualTransformation): VisualTransformation {
    return if (visualTransformation == VisualTransformation.None) {
        PasswordVisualTransformation()
    } else {
        VisualTransformation.None
    }
}
package com.sgg.cinematics.ui.screen.account

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.sgg.cinematics.R
import com.sgg.cinematics.data.model.AuthData
import com.sgg.cinematics.data.model.UserModel
import com.sgg.cinematics.ui.commonui.BackNavigationFab
import com.sgg.cinematics.ui.commonui.ControlledOutlinedTextField
import com.sgg.cinematics.ui.commonui.CustomDropdownMenu
import com.sgg.cinematics.ui.commonui.PasswordTextFieldWrapper
import com.sgg.cinematics.ui.ui.theme.CinematicsTheme
import com.sgg.cinematics.ui.ui.theme.md_theme_light_onSecondary
import com.sgg.cinematics.ui.ui.theme.md_theme_light_secondary
import com.sgg.cinematics.utils.DarkAndLightPreview
import com.sgg.cinematics.utils.InputError
import com.sgg.cinematics.utils.millisToLocalDate
import com.sgg.cinematics.utils.validateEmail
import com.sgg.cinematics.utils.validatePassword

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
            ProfilePicture(onPictureSelected = { uri ->
                uri?.let {
                    viewModel.updateProfilePictureUri(it)
                }
            })

            UserFullName(userInfo = user,
                         onValueChange = { user ->
                             viewModel.updateUserInfo(user)
                         })

            UserEmail(userInfo = user,
                      emailValidation = {
                          validateEmail(it)
                      },
                      onValueChange = { user ->
                          viewModel.updateUserInfo(user)
                          viewModel.updateAuthData(authData.copy(email = user.email ?: ""))
                      })

            PasswordInputs(authData = authData,
                           onValueChange = { authData ->
                               viewModel.updateAuthData(authData)
                           })

            BirthDatePicker(modifier = Modifier.fillMaxWidth(),
                            userInfo = user,
                            onDateSelected = { userInfo ->
                                viewModel.updateUserInfo(userInfo)
                            })

            GenderInput(userInfo = user,
                        onValueChange = { user ->
                            viewModel.updateUserInfo(user)
                        })

            Location(onValueChange = {})

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

/**
 * Composable to display profile picture and let user to change it from camera or from photo picker
 *
 * @param modifier: A modifier with default value [Modifier]
 * @param picture
 * @param onPictureSelected
 */
@Composable
fun ProfilePicture(
    modifier: Modifier = Modifier,
    picture: Painter = painterResource(id = R.drawable.default_user_profile),
    onPictureSelected: (Uri?) -> Unit
) {

    var pickPhotoDialogIsVisible by rememberSaveable {
        mutableStateOf(false)
    }

    var selectedImage by remember {
        mutableStateOf<Uri?>(null)
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uri ->
        selectedImage = uri
        onPictureSelected(uri)
    }

    Box(modifier = modifier.padding(8.dp)) {
        AsyncImage(modifier = Modifier
            .size(200.dp)
            .clip(CircleShape)
            .border(BorderStroke(4.dp, MaterialTheme.colorScheme.outline), CircleShape)
            .padding(4.dp)
            .align(alignment = Alignment.Center),
                   model = selectedImage,
                   contentScale = ContentScale.Crop,
                   contentDescription = "",
                   placeholder = painterResource(id = R.drawable.default_user_profile)
        )
        IconButton(modifier = Modifier.align(alignment = Alignment.BottomEnd),
                   onClick = { pickPhotoDialogIsVisible = true }) {
            Icon(modifier = Modifier.size(32.dp),
                 painter = painterResource(id = R.drawable.icon_photo_camera_24px),
                 tint = MaterialTheme.colorScheme.primary,
                 contentDescription = ""
            )
        }
        if (pickPhotoDialogIsVisible) {
            Dialog(onDismissRequest = { pickPhotoDialogIsVisible = false }) {
                Card() {
                    Column(modifier = Modifier.padding(8.dp),
                           horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OutlinedButton(modifier = Modifier.fillMaxWidth(),
                                       shape = MaterialTheme.shapes.small,
                                       onClick = { pickPhotoDialogIsVisible = false }
                        ) {
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Icon(painter = painterResource(id = R.drawable.icon_camera_24px),
                                     contentDescription = ""
                                )
                                Text(text = stringResource(id = R.string.txt_camera_option))
                            }
                        }
                        OutlinedButton(modifier = Modifier.fillMaxWidth(),
                                       shape = MaterialTheme.shapes.small,
                                       onClick = {
                                           photoPickerLauncher.launch(
                                                   PickVisualMediaRequest(
                                                           ActivityResultContracts.PickVisualMedia.ImageOnly
                                                   )
                                           )
                                           pickPhotoDialogIsVisible = false
                                       }
                        ) {
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Icon(painter = painterResource(id = R.drawable.icon_upload_24px),
                                     contentDescription = ""
                                )
                                Text(text = stringResource(id = R.string.txt_upload_option))
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * user first name and last name input
 *
 * @param modifier: A modifier with default value [Modifier]
 * @param userInfo: current [UserModel] tha hold user info from input
 * @param onValueChange: callback to update [UserModel.firstName] and [UserModel.lastName]
 */
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
                              modifier = Modifier.fillMaxWidth(),
                              label = {
                                  Text(text = stringResource(id = R.string.label_first_name),
                                       style = MaterialTheme.typography.bodySmall
                                  )
                              },
                              onValueChange = { firstName ->
                                  onValueChange(userInfo.copy(lastName = firstName))
                              })
            OutlinedTextField(value = userInfo.lastName,
                              modifier = Modifier.fillMaxWidth(),
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

/**
 * Input for user email
 *
 * @param modifier: A modifier with default value [Modifier]
 * @param userInfo: current [UserModel] tha hold user info from input
 * @param emailValidation: email validator function to check if it mach pattern
 * @param onValueChange: callback to update [UserModel.email]
 */
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

/**
 * Password input with conformation input. Show error if password don't match pattern and
 * confirmation password doesn't match.
 * This composable take an [AuthData] object as argument that will be used to create user account
 * on firebase with email/password login type
 *
 * @param modifier: A modifier with default value [Modifier]
 * @param authData: current [AuthData] tha hold user sign info from input
 * @param onValueChange: callback to update [AuthData.password]
 */
@Composable
fun PasswordInputs(
    modifier: Modifier = Modifier,
    authData: AuthData,
    onValueChange: (AuthData) -> Unit,
) {

    val passwordConfirmation = remember {
        mutableStateOf("")
    }

    val passwordError = remember {
        mutableStateOf(InputError(haveError = false,
                                  messageResourceId = R.string.password_pattern_error
        )
        )
    }

    val confirmationPasswordError = remember {
        mutableStateOf(InputError(haveError = false,
                                  messageResourceId = R.string.password_confirmation_error
        )
        )
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
            PasswordTextFieldWrapper(value = authData.password,
                                     isError = passwordError.value.haveError,
                                     errorMessage = stringResource(id = passwordError.value.messageResourceId),
                                     placeHolder = stringResource(id = R.string.placeholder_password),
                                     onPasswordChange = { password ->
                                         onValueChange(authData.copy(password = password))
                                         passwordError.value.haveError = !validatePassword(authData.password)
                                     })
            PasswordTextFieldWrapper(value = passwordConfirmation.value,
                                     isError = confirmationPasswordError.value.haveError,
                                     errorMessage = stringResource(id = confirmationPasswordError.value.messageResourceId),
                                     placeHolder = stringResource(id = R.string.placeholder_confirm_password),
                                     onPasswordChange = {
                                         passwordConfirmation.value = it
                                         confirmationPasswordError.value.haveError = passwordConfirmation.value != authData.password
                                     })
        }
    }
}

/**
 * This input is for user birth date, it can be updated manually or from date picker
 *
 * @param modifier: A modifier with default value [Modifier]
 * @param userInfo: current [UserModel] tha hold user info from input
 * @param onDateSelected: callback to update [UserModel.birthDate]
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthDatePicker(
    modifier: Modifier = Modifier,
    userInfo: UserModel,
    onDateSelected: (UserModel) -> Unit
) {

    var selectedValue by remember {
        mutableStateOf(0L)
    }

    val datePickerState = rememberDatePickerState()

    var datePickerDialogIsVisible by rememberSaveable {
        mutableStateOf(false)
    }

    datePickerState.selectedDateMillis?.let {
        selectedValue = it
    }

    val displayedDate = if (userInfo.birthDate == null) "" else userInfo.birthDate.toString()

    Row(modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(tint = MaterialTheme.colorScheme.onSurface,
             painter = painterResource(id = R.drawable.icon_birth_24px),
             contentDescription = ""
        )

        OutlinedTextField(modifier = Modifier.weight(0.9f),
                          label = {
                              Text(text = stringResource(id = R.string.label_birth_date),
                                   style = MaterialTheme.typography.bodySmall
                              )
                          },
                          value = displayedDate,
                          onValueChange = {})

        IconButton(modifier = Modifier.weight(0.1f),
                   onClick = {
                       datePickerDialogIsVisible = true
                   }) {
            Icon(painter = painterResource(id = R.drawable.icon_calendar_24px),
                 contentDescription = ""
            )
        }

        if (datePickerDialogIsVisible) {
            DatePickerDialog(onDismissRequest = { datePickerDialogIsVisible = false },
                             confirmButton = {
                                 TextButton(onClick = {
                                     datePickerDialogIsVisible = false
                                     onDateSelected(userInfo.copy(birthDate = selectedValue.millisToLocalDate()))
                                 }) {
                                     Text(text = stringResource(id = R.string.txt_ok))
                                 }
                             },
                             dismissButton = {
                                 TextButton(onClick = { datePickerDialogIsVisible = false }) {
                                     Text(text = stringResource(id = R.string.txt_cancel))
                                 }
                             }) {
                DatePicker(state = datePickerState)
            }
        }
    }
}

@Composable
fun GenderInput(
    modifier: Modifier = Modifier,
    userInfo: UserModel,
    onValueChange: (UserModel) -> Unit
) {
    Row(modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(tint = MaterialTheme.colorScheme.onSurface,
             painter = painterResource(id = R.drawable.icon_men_24px),
             contentDescription = ""
        )
        CustomDropdownMenu(modifier = Modifier.fillMaxWidth(),
                           selectedValue = userInfo.gender ?: "",
                           optionList = stringArrayResource(id = R.array.gender_list).toList(),
                           textLabel = stringResource(id = R.string.label_gender),
                           onValueChange = { gender ->
                               onValueChange(userInfo.copy(gender = gender))
                           }
        )

    }
}

@Composable
fun Location(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {
    Row(modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(tint = MaterialTheme.colorScheme.onSurface,
             painter = painterResource(id = R.drawable.icon_home_pin_24px),
             contentDescription = ""
        )
        OutlinedTextField(value = "",
                          modifier = Modifier.fillMaxWidth(),
                          label = {
                              Text(text = stringResource(id = R.string.label_location),
                                   style = MaterialTheme.typography.bodySmall
                              )
                          },
                          trailingIcon = {
                              IconButton(onClick = {
                                  //TODO implement reverse geocoding
                              }) {
                                  Icon(painter = painterResource(id = R.drawable.icon_my_location_24px),
                                       contentDescription = ""
                                  )
                              }
                          },
                          onValueChange = onValueChange
        )
    }
}

@DarkAndLightPreview
@Composable
fun CreateAccountScreenPreview() {
    CinematicsTheme {
        CreateAccountScreen(onNavigateBack = {}, onCreateAccountClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePicturePreview() {
    CinematicsTheme {
        ProfilePicture() {

        }
    }
}

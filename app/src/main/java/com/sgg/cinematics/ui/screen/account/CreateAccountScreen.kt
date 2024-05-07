package com.sgg.cinematics.ui.screen.account

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sgg.cinematics.BuildConfig
import com.sgg.cinematics.R
import com.sgg.cinematics.data.model.AuthData
import com.sgg.cinematics.data.model.UserModel
import com.sgg.cinematics.ui.commonui.BackNavigationFab
import com.sgg.cinematics.ui.commonui.CameraButton
import com.sgg.cinematics.ui.commonui.ControlledOutlinedTextField
import com.sgg.cinematics.ui.commonui.CustomDropdownMenu
import com.sgg.cinematics.ui.commonui.LoadingScreen
import com.sgg.cinematics.ui.commonui.PasswordTextFieldWrapper
import com.sgg.cinematics.ui.ui.theme.CinematicsTheme
import com.sgg.cinematics.ui.ui.theme.md_theme_light_onSecondary
import com.sgg.cinematics.ui.ui.theme.md_theme_light_secondary
import com.sgg.cinematics.utils.DarkAndLightPreview
import com.sgg.cinematics.utils.InputError
import com.sgg.cinematics.utils.UiState
import com.sgg.cinematics.utils.currentDateAsString
import com.sgg.cinematics.utils.validateEmail
import com.sgg.cinematics.utils.validatePassword
import kotlinx.coroutines.launch
import java.io.File
import java.time.LocalDateTime
import java.util.Objects

@Composable
fun CreateAccountScreen(
        modifier: Modifier = Modifier,
        onNavigateBack: () -> Unit,
        navigateToUserProfile: () -> Unit
) {

    val scrollState = rememberScrollState()

    val viewModel = hiltViewModel<CreatAccountViewModel>()

    val userInfo = viewModel.userInfo

    val authData = viewModel.authData

    val photoUri = viewModel.profilePictureUri

    val uiState = viewModel.uiState

    val pictureAnimatable = remember {
        Animatable(0f)
    }

    val leftOffsetAnimatable = remember {
        Animatable(-2500f)
    }

    val rightOffsetAnimatable = remember {
        Animatable(2500f)
    }

    LaunchedEffect(key1 = uiState.value) {
        if (uiState.value is UiState.Success) {
            navigateToUserProfile()
        }
    }

    LaunchedEffect(key1 = Unit) {
        launch {
            pictureAnimatable.animateTo(targetValue = 1f,
                                        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy))
        }
        launch {
            leftOffsetAnimatable.animateTo(targetValue = 0f,
                                           animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy))

        }
        launch {
            rightOffsetAnimatable.animateTo(targetValue = 0f,
                                            animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy))
        }
    }

    Box {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp),
               horizontalAlignment = Alignment.CenterHorizontally,
               modifier = modifier
                   .fillMaxSize()
                   .padding(16.dp)
                   .verticalScroll(scrollState)
        ) {
            ProfilePicture(modifier = Modifier.scale(pictureAnimatable.value),
                           photoUri = photoUri,
                           onPictureUpdated = { uri ->
                               viewModel.updateProfilePictureUri(uri)
                           })

            UserFullName(modifier = Modifier.offset(x = leftOffsetAnimatable.value.dp),
                         userInfo = userInfo,
                         onValueChange = { user ->
                             viewModel.updateUserInfo(user)
                         })

            UserEmail(modifier = Modifier.offset(x = rightOffsetAnimatable.value.dp),
                      userInfo = userInfo,
                      emailValidation = {
                          validateEmail(it)
                      },
                      onValueChange = { user ->
                          viewModel.updateUserInfo(user)
                          viewModel.updateAuthData(authData.copy(email = user.email ?: ""))
                      })

            PasswordInputs(modifier = Modifier.offset(x = leftOffsetAnimatable.value.dp),
                           authData = authData,
                           onValueChange = { authData ->
                               viewModel.updateAuthData(authData)
                           })

            BirthDatePicker(modifier = Modifier
                .fillMaxWidth()
                .offset(x = rightOffsetAnimatable.value.dp),
                            userInfo = userInfo,
                            onDateSelected = { userInfo ->
                                viewModel.updateUserInfo(userInfo)
                            })

            GenderInput(modifier = Modifier.offset(x = leftOffsetAnimatable.value.dp),
                        userInfo = userInfo,
                        onValueChange = { user ->
                            viewModel.updateUserInfo(user)
                        })

            Location(modifier = Modifier.offset(x = rightOffsetAnimatable.value.dp),
                     onValueChange = {})

            Row(modifier = Modifier.offset(x = leftOffsetAnimatable.value.dp)) {
                Spacer(modifier = Modifier.size(32.dp))
                Button(onClick = { viewModel.createAccount() },
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
        AnimatedVisibility(visible = uiState.value is UiState.Loading,
                           enter = fadeIn(),
                           exit = fadeOut()) {
            LoadingScreen(modifier = Modifier.background(color = Color.Black.copy(alpha = 0.6f)))
        }
    }
}

/**
 * Composable to display profile picture and let user to change it from camera or from photo picker
 *
 * @param modifier: A modifier with default value [Modifier]
 * @param picture
 * @param onPictureUpdated
 */
@Composable
fun ProfilePicture(
        modifier: Modifier = Modifier,
        photoUri: Uri,
        onPictureUpdated: (Uri) -> Unit
) {
    val context = LocalContext.current

    var photoFileUri by remember { mutableStateOf(Uri.EMPTY) }

    var pickPhotoDialogIsVisible by rememberSaveable {
        mutableStateOf(false)
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uri ->
        uri?.let {
            onPictureUpdated(it)
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicture()) { approved ->
        if (approved) {
            onPictureUpdated(photoFileUri)
        } else {
            Toast.makeText(context, R.string.txt_error_permission, Toast.LENGTH_SHORT)
                .show()
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { approved ->
        if (approved) {
            cameraLauncher.launch(photoFileUri)
        } else {
            Toast.makeText(context, R.string.txt_error_permission, Toast.LENGTH_SHORT)
                .show()
        }
    }

    Box(modifier = modifier.padding(8.dp)) {
        AsyncImage(modifier = Modifier
            .size(200.dp)
            .clip(CircleShape)
            .border(BorderStroke(4.dp, MaterialTheme.colorScheme.outline), CircleShape)
            .padding(4.dp)
            .align(alignment = Alignment.Center),
                   model = ImageRequest.Builder(LocalContext.current)
                       .crossfade(true)
                       .placeholder(R.drawable.default_user_profile)
                       .data(photoUri)
                       .build(),
                   contentScale = ContentScale.Crop,
                   contentDescription = ""
        )
        CameraButton(modifier = Modifier.align(alignment = Alignment.BottomEnd),
                     onButtonClick = { pickPhotoDialogIsVisible = true })
        if (pickPhotoDialogIsVisible) {
            PhotoPickerDialog(
                    onCameraOptionClick = {
                        photoFileUri = createFileUri(context)
                        launchCamera(context = context,
                                     photoFileUri = photoFileUri,
                                     cameraLauncher = cameraLauncher,
                                     permissionLauncher = permissionLauncher
                        )
                        pickPhotoDialogIsVisible = false
                    },
                    onPhotoPickerOptionClick = {
                        photoPickerLauncher.launch(
                                PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                        )
                        pickPhotoDialogIsVisible = false
                    },
                    onDismissRequest = { pickPhotoDialogIsVisible = false }
            )
        }
    }
}

@Composable
fun PhotoPickerDialog(
        modifier: Modifier = Modifier,
        onCameraOptionClick: () -> Unit,
        onPhotoPickerOptionClick: () -> Unit,
        onDismissRequest: () -> Unit,
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card {
            Column(modifier = modifier.padding(8.dp),
                   horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedButton(modifier = Modifier.fillMaxWidth(),
                               shape = MaterialTheme.shapes.small,
                               onClick = onCameraOptionClick
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
                               onClick = onPhotoPickerOptionClick
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
                                  Text(
                                          text = stringResource(id = R.string.label_first_name),
                                          style = MaterialTheme.typography.bodyLarge
                                  )
                              },
                              onValueChange = { firstName ->
                                  onValueChange(userInfo.copy(firstName = firstName))
                              })
            OutlinedTextField(value = userInfo.lastName,
                              modifier = Modifier.fillMaxWidth(),
                              label = {
                                  Text(
                                          text = stringResource(id = R.string.label_last_name),
                                          style = MaterialTheme.typography.bodyLarge
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
    Row(modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(tint = MaterialTheme.colorScheme.onSurface,
             painter = painterResource(id = R.drawable.icon_mail_24px),
             contentDescription = ""
        )
        ControlledOutlinedTextField(value = userInfo.email,
                                    iconResId = R.drawable.icon_mail_24px,
                                    placeholderResId = R.string.label_user_mail,
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
                                  messageResourceId = R.string.password_pattern_error)
        )
    }

    val confirmationPasswordError = remember {
        mutableStateOf(InputError(haveError = false,
                                  messageResourceId = R.string.password_confirmation_error)
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

    val displayedDate = if (userInfo.birthDate == null) "" else userInfo.displayedBirthDate

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
                                   style = MaterialTheme.typography.bodyLarge
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
                                     onDateSelected(userInfo.copy(birthDate = selectedValue))
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
                                   style = MaterialTheme.typography.bodyLarge
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
        CreateAccountScreen(onNavigateBack = {}, navigateToUserProfile = {})
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePicturePreview() {
    CinematicsTheme {
        ProfilePicture(photoUri = Uri.EMPTY) {

        }
    }
}

private fun createFileUri(context: Context): Uri {
    val file = context.createImageFile()
    return FileProvider.getUriForFile(
            Objects.requireNonNull(context),
            BuildConfig.APPLICATION_ID + ".provider", file
    )
}

private fun Context.createImageFile(): File {
    val fileName = "cinematics_${
        LocalDateTime.now()
            .currentDateAsString()
    }"
    return File.createTempFile(
            fileName,
            ".jpg",
            externalCacheDir
    )
}

private fun launchCamera(
        context: Context,
        photoFileUri: Uri,
        cameraLauncher: ActivityResultLauncher<Uri>,
        permissionLauncher: ActivityResultLauncher<String>
) {
    val permissionCheckResult = ContextCompat.checkSelfPermission(context,
                                                                  Manifest.permission.CAMERA
    )
    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
        cameraLauncher.launch(photoFileUri)
    } else {
        permissionLauncher.launch(Manifest.permission.CAMERA)
    }
}
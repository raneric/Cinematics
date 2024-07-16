package com.sgg.cinematics.ui.screen.profile

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sgg.cinematics.R
import com.sgg.cinematics.data.model.UserModel
import com.sgg.cinematics.data.userModelLists
import com.sgg.cinematics.ui.ui.theme.CinematicsTheme
import com.sgg.cinematics.ui.ui.theme.ProfileBackgroundShape
import com.sgg.cinematics.ui.ui.theme.ShapeCutPosition
import com.sgg.cinematics.ui.ui.theme.custom_green_btn_color
import com.sgg.cinematics.ui.ui.theme.thick_divider_color
import com.sgg.cinematics.ui.ui.theme.thin_divider_color
import com.sgg.cinematics.ui.ui.theme.userBioText
import com.sgg.cinematics.ui.ui.theme.userProfileContent
import com.sgg.cinematics.ui.ui.theme.userProfileTitle
import com.sgg.cinematics.utils.DarkAndLightPreview

@Composable
fun UserProfileScreen(
    user: UserModel?,
    logout: () -> Unit,
    onEditClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val fabSize = with(LocalDensity.current) { 86.dp.toPx() }
    val scrollState = rememberScrollState()

    val interactionSource = remember {
        MutableInteractionSource()
    }

    val fabScale = remember {
        Animatable(initialValue = 1f)
    }

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect {
            when (it) {
                is PressInteraction.Press -> fabScale.animateTo(targetValue = 0.8f)
                is PressInteraction.Release -> fabScale.animateTo(
                    targetValue = 1f,
                    animationSpec = spring(
                        stiffness = 600f,
                        dampingRatio = 0.2f
                    )
                )
            }
        }
    }

    user?.let {
        UserProfileLayout(modifier = modifier
            .padding(16.dp)
            .verticalScroll(scrollState),
                          pictureSection = {
                              ProfilePictureSection(
                                  user = it,
                                  logout = logout,
                                  fabSize = fabSize
                              )
                          },
                          fab = {
                              FloatingActionButton(
                                  onClick = onEditClicked,
                                  modifier = Modifier
                                      .size(80.dp)
                                      .graphicsLayer {
                                          scaleX = fabScale.value
                                          scaleY = fabScale.value
                                      },
                                  interactionSource = interactionSource,
                                  elevation = FloatingActionButtonDefaults.elevation(12.dp),
                                  containerColor = custom_green_btn_color,
                                  contentColor = MaterialTheme.colorScheme.onPrimary,
                                  shape = CircleShape
                              ) {
                                  Icon(
                                      modifier = Modifier.size(32.dp),
                                      painter = painterResource(id = R.drawable.icon_edit_32),
                                      contentDescription = stringResource(id = R.string.content_descrip_back_fab)
                                  )
                              }
                          },
                          userInfo = {
                              UserInfoSection(user = user, fabSize = fabSize)
                          })
    }
}

@Composable
fun UserProfileLayout(
    pictureSection: @Composable () -> Unit,
    fab: @Composable () -> Unit,
    userInfo: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {

    Layout(
        contents = listOf(pictureSection, fab, userInfo),
        modifier = modifier
    ) { (pictureSectionMeasurable, fabMeasurable, userInfoMeasurable),
        constraint ->

        val sectionMargin = 24.dp.toPx()

        val picturePlaceable = pictureSectionMeasurable.first()
            .measure(constraint)
        val fabPlaceable = fabMeasurable.first()
            .measure(constraint)
        val userInfoPlaceable = userInfoMeasurable.first()
            .measure(constraint)

        val fabOverflow = fabPlaceable.width / 6

        val userInfoY = (picturePlaceable.height + sectionMargin).toInt()

        val layoutWidth = picturePlaceable.width + (fabOverflow * 2)
        val layoutHeight =
            (picturePlaceable.height + sectionMargin + userInfoPlaceable.height).toInt()

        val fabY = picturePlaceable.height + (sectionMargin / 2).toInt() - (fabPlaceable.height / 2)
        val fabX = picturePlaceable.width - fabPlaceable.width + (fabOverflow * 2)

        layout(width = layoutWidth, height = layoutHeight) {
            picturePlaceable.place(x = fabOverflow, y = 0)
            fabPlaceable.place(x = fabX, y = fabY, zIndex = 2f)
            userInfoPlaceable.place(x = fabOverflow, y = userInfoY)
        }
    }
}

@Composable
fun ProfilePictureSection(
    user: UserModel,
    fabSize: Float,
    logout: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
                .clip(MaterialTheme.shapes.medium)
                .clip(ProfileBackgroundShape(fabSize, ShapeCutPosition.BOTTOM_RIGHT))
                .background(MaterialTheme.colorScheme.onSurface)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .crossfade(true)
                    .placeholder(R.drawable.default_user_profile)
                    .data(user.pictureUrl)
                    .build(),
                contentDescription = stringResource(id = R.string.content_descrip_photo_profile),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(240.dp)
                    .clip(CircleShape)
                    .border(2.dp, custom_green_btn_color, CircleShape),
            )
            Text(text = user.fullName, style = userProfileTitle)
        }
        Column {
            IconButton(onClick = logout) {
                Icon(
                    tint = Color.Black,
                    painter = painterResource(id = R.drawable.icon_logout_24px),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun UserInfoSection(
    user: UserModel,
    fabSize: Float,
    modifier: Modifier = Modifier
) {
    val spacerSize = 4.dp
    val defaultText = stringResource(id = R.string.txt_not_available)

    val genderIcon =
        if (user.gender == "Male") R.drawable.icon_male_24px else R.drawable.icon_female_24px

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .clip(ProfileBackgroundShape(fabSize, ShapeCutPosition.TOP_RIGHT))
            .background(MaterialTheme.colorScheme.onSurface)
            .padding(10.dp)
    ) {
        Text(
            text = stringResource(id = R.string.txt_user_info_tittle),
            style = userProfileTitle
        )
        HorizontalDivider(color = thick_divider_color)
        Spacer(modifier = Modifier.size(spacerSize))

        UserInfoItem(
            text = stringResource(id = R.string.txt_birth_date, user.displayedBirthDate),
            R.drawable.icon_birthday_date
        )
        HorizontalDivider(color = thin_divider_color)
        Spacer(modifier = Modifier.size(spacerSize))

        UserInfoItem(
            text = stringResource(
                id = R.string.txt_gender,
                user.gender ?: defaultText
            ),
            icon = genderIcon
        )

        HorizontalDivider(color = thin_divider_color)

        Spacer(modifier = Modifier.size(spacerSize))

        UserInfoItem(
            text = stringResource(
                id = R.string.txt_email,
                user.email ?: defaultText
            ),
            R.drawable.icon_email
        )
        HorizontalDivider(color = thin_divider_color)
        Spacer(modifier = Modifier.size(spacerSize))

        UserInfoItem(
            text = stringResource(
                id = R.string.txt_location,
                user.location ?: defaultText
            ),
            R.drawable.icon_location
        )
        HorizontalDivider(color = thin_divider_color)
        Spacer(modifier = Modifier.size(spacerSize))

        Bio(text = user.bio ?: defaultText)
    }
}

@Composable
fun Bio(
    text: String,
    modifier: Modifier = Modifier
) {
    BioLayout(tittle = {
        Text(
            text = stringResource(id = R.string.txt_bio_tittle),
            style = userProfileTitle,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.onSurface)
                .widthIn(min = 30.dp)
        )
    }, textBio = {
        Text(
            text = text,
            style = userBioText,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    BorderStroke(1.dp, thin_divider_color),
                    shape = MaterialTheme.shapes.small
                )
                .padding(horizontal = 8.dp, vertical = 12.dp)
        )
    }, modifier = modifier)
}

@Composable
fun BioLayout(
    tittle: @Composable () -> Unit,
    textBio: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Layout(
        contents = listOf(tittle, textBio),
        modifier = modifier
    ) { (titleMeasurable, textMeasurable), constraint ->
        val tittlePlaceable = titleMeasurable.first()
            .measure(constraint)
        val textPlaceable = textMeasurable.first()
            .measure(constraint)
        val tittleMargin = 16.dp.toPx()
            .toInt()
        val layoutHeight = tittlePlaceable.height + textPlaceable.height

        layout(width = textPlaceable.width, height = layoutHeight) {
            tittlePlaceable.place(x = tittleMargin, y = 0, zIndex = 1f)
            textPlaceable.place(x = 0, y = tittlePlaceable.height / 2)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserProfileScreenPreview() {
    CinematicsTheme {
        UserProfileScreen(user = userModelLists[0],
                          logout = {},
                          onEditClicked = {})
    }
}

@Composable
fun UserInfoItem(
    text: String,
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier
) {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = modifier) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = "TODO",
            modifier = Modifier.size(16.dp)
        )
        Text(text = text, style = userProfileContent)
    }
}

@DarkAndLightPreview
@Composable
fun UserInfoSectionPreview() {
    val fabSize = with(LocalDensity.current) { 60.dp.toPx() }
    CinematicsTheme {
        UserInfoSection(user = userModelLists[0], fabSize = fabSize)
    }
}

@DarkAndLightPreview
@Composable
fun ProfilePictureSectionPreview() {
    val fabSize = with(LocalDensity.current) { 60.dp.toPx() }
    CinematicsTheme {
        ProfilePictureSection(user = userModelLists[0], logout = {}, fabSize = fabSize)
    }
}
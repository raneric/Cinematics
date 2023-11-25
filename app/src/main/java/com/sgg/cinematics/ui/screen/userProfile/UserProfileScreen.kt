package com.sgg.cinematics.ui.screen.userProfile

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sgg.cinematics.R
import com.sgg.cinematics.data.model.UserModel
import com.sgg.cinematics.data.userModelLists
import com.sgg.cinematics.ui.ui.theme.CinematicsTheme
import com.sgg.cinematics.ui.ui.theme.ProfileBackgroundShape
import com.sgg.cinematics.ui.ui.theme.ShapeCutPosition
import com.sgg.cinematics.ui.ui.theme.profile_background_color
import com.sgg.cinematics.ui.ui.theme.userBioText
import com.sgg.cinematics.ui.ui.theme.userProfileContent
import com.sgg.cinematics.ui.ui.theme.userProfileTitle
import com.sgg.cinematics.ui.ui.theme.watched_btn_color

@Composable
fun UserProfileScreen(user: UserModel,
                      modifier: Modifier = Modifier) {
    val fabSize = with(LocalDensity.current) { 64.dp.toPx() }
    val scrollState = rememberScrollState()
    val interactionSource = remember { MutableInteractionSource() }

    val fabScale = remember {
        Animatable(1f)
    }

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect {
            when (it) {
                is PressInteraction.Press -> fabScale.animateTo(targetValue = 0.8f)
                is PressInteraction.Release -> fabScale.animateTo(targetValue = 1f)
            }
        }
    }

    UserProfileLayout(modifier = modifier
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(scrollState), pictureSection = {
        ProfilePictureSection(user = user, fabSize = fabSize)
    }, fab = {
        FloatingActionButton(onClick = {},
                             modifier = Modifier
                                     .graphicsLayer {
                                         scaleY = fabScale.value
                                         scaleY = fabScale.value
                                     },
                             interactionSource = interactionSource,
                             elevation = FloatingActionButtonDefaults.elevation(12.dp),
                             containerColor = watched_btn_color,
                             contentColor = MaterialTheme.colorScheme.onPrimary,
                             shape = CircleShape) {
            Icon(painter = painterResource(id = R.drawable.icon_edit_32),
                 contentDescription = stringResource(id = R.string.content_descrip_back_fab))
        }
    }, userInfo = {
        UserInfoSection(user = user, fabSize = fabSize)
    })
}

@Composable
fun UserProfileLayout(pictureSection: @Composable () -> Unit,
                      fab: @Composable () -> Unit,
                      userInfo: @Composable () -> Unit,
                      modifier: Modifier = Modifier) {

    Layout(contents = listOf(pictureSection, fab, userInfo),
           modifier = modifier) { (pictureSectionMeasurable, fabMeasurable, userInfoMeasurable), constraint ->

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
        val layoutHeight = (picturePlaceable.height + sectionMargin + userInfoPlaceable.height).toInt()

        val fabY = +picturePlaceable.height + (sectionMargin / 2).toInt() - (fabPlaceable.height / 2)
        val fabX = picturePlaceable.width - fabPlaceable.width + (fabOverflow * 2)

        layout(width = layoutWidth, height = layoutHeight) {
            picturePlaceable.place(x = fabOverflow, y = 0)
            fabPlaceable.place(x = fabX, y = fabY, zIndex = 2f)
            userInfoPlaceable.place(x = fabOverflow, y = userInfoY)
        }
    }
}

@Composable
fun ProfilePictureSection(user: UserModel,
                          fabSize: Float,
                          modifier: Modifier = Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
           verticalArrangement = Arrangement.SpaceAround,
           modifier = Modifier
                   .fillMaxWidth()
                   .height(275.dp)
                   .clip(MaterialTheme.shapes.medium)
                   .clip(ProfileBackgroundShape(fabSize, ShapeCutPosition.BOTTOM_RIGHT))
                   .background(profile_background_color)) {
        Image(
            painter = painterResource(id = user.picture),
            contentDescription = "",
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape),
        )
        Text(text = user.name, style = userProfileTitle)
    }
}

@Composable
fun UserInfoSection(user: UserModel,
                    fabSize: Float,
                    modifier: Modifier = Modifier) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp),
           modifier = modifier
                   .fillMaxWidth()
                   .clip(MaterialTheme.shapes.medium)
                   .clip(ProfileBackgroundShape(fabSize, ShapeCutPosition.TOP_RIGHT))
                   .background(profile_background_color)
                   .padding(10.dp)) {
        Text(text = stringResource(id = R.string.txt_user_info_tittle), style = userProfileTitle)
        Divider(color = Color(0xFFD1D1D1))
        UserInfoItem(text = user.userName, R.drawable.icon_user_name)
        Divider(color = Color(0xFFE1E1E1))
        UserInfoItem(text = user.birthDate.toString(), R.drawable.icon_birthday_date)
        Divider(color = Color(0xFFE1E1E1))
        UserInfoItem(text = user.email, R.drawable.icon_email)
        Divider(color = Color(0xFFE1E1E1))
        UserInfoItem(text = user.address, R.drawable.icon_location)
        Bio(text = user.bio ?: "N/A")


    }
}

@Composable
fun Bio(text: String,
        modifier: Modifier = Modifier) {
    BioLayout(tittle = {
        Text(text = stringResource(id = R.string.txt_bio_tittle),
             style = userProfileTitle,
             textAlign = TextAlign.Center,
             modifier = Modifier
                     .background(color = profile_background_color)
                     .widthIn(min = 30.dp))
    }, textBio = {
        Text(text = text,
             style = userBioText,
             modifier = Modifier
                     .fillMaxWidth()
                     .border(BorderStroke(1.dp, Color(0xFFE1E1E1)),
                             shape = MaterialTheme.shapes.small)
                     .padding(horizontal = 8.dp, vertical = 12.dp))
    }, modifier = modifier)
}

@Composable
fun BioLayout(tittle: @Composable () -> Unit,
              textBio: @Composable () -> Unit,
              modifier: Modifier = Modifier) {
    Layout(contents = listOf(tittle, textBio),
           modifier = modifier) { (titleMeasurable, textMeasurable), constraint ->
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
        UserProfileScreen(user = userModelLists[0])
    }
}

@Composable
fun UserInfoItem(text: String,
                 @DrawableRes icon: Int,
                 modifier: Modifier = Modifier) {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = modifier) {
        Image(painter = painterResource(id = icon),
              contentDescription = "TODO",
              modifier = Modifier.size(16.dp))
        Text(text = text, style = userProfileContent)
    }
}

@Preview(showBackground = true)
@Composable
fun UserInfoSectionPreview() {
    val fabSize = with(LocalDensity.current) { 60.dp.toPx() }
    CinematicsTheme {
        UserInfoSection(user = userModelLists[0], fabSize = fabSize)
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePictureSectionPreview() {
    val fabSize = with(LocalDensity.current) { 60.dp.toPx() }
    CinematicsTheme {
        ProfilePictureSection(user = userModelLists[0], fabSize = fabSize)
    }
}
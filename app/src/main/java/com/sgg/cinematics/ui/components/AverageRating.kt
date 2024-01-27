package com.sgg.cinematics.ui.components

import androidx.annotation.IntRange
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sgg.cinematics.R
import com.sgg.cinematics.data.model.UserRatingModel
import com.sgg.cinematics.data.userRatingModelLists
import com.sgg.cinematics.ui.ui.theme.CinematicsTheme
import com.sgg.cinematics.ui.ui.theme.md_theme_light_onPrimary
import com.sgg.cinematics.ui.ui.theme.ratingTypo
import com.sgg.cinematics.ui.ui.theme.rating_negative
import com.sgg.cinematics.ui.ui.theme.rating_positive
import com.sgg.cinematics.utils.formatDate

/**
 * A composable that display the average rating the movies got
 * @param ratingStars: Int value of the average stars for [StarRating] composable
 * @param ratingValue: String of the double value for the average rating note
 * @param modifier: A modifier with default value [Modifier]
 */
@Composable
fun AverageRating(
        ratingStars: Int,
        ratingValue: String,
        textColor: Color = md_theme_light_onPrimary,
        modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier) {
        Text(
            text = ratingValue,
            style = ratingTypo,
            color = textColor)
        StarRating(ratingStars)
    }
}

/**
 * A composable that display a average rating as title in the movies list details screen
 * @param ratingStars: Int value of the average stars for [StarRating] composable
 * @param ratingValue: String of the double value for the average rating note
 * @param modifier: A modifier with default value [Modifier]
 */
@Composable
fun AverageDetailRating(
        ratingStars: Int,
        ratingValue: Double,
        modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(id = R.string.txt_rating_section, ratingValue),
            style = MaterialTheme.typography.titleMedium)
        StarRating(ratingStars = ratingStars)
    }
}

/**
 * User rating list
 * @param ratingList : List of [UserRatingModel] composable
 * @param modifier: A modifier with default value [Modifier]
 */
@Composable
fun UserRatings(
        ratingList: List<UserRatingModel>,
        modifier: Modifier = Modifier
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp), modifier = modifier) {
        ratingList.forEach {
            RatingRow(userRatingModel = it)
        }
    }
}

/**
 * User rating row composable to display each user rating note
 * @param userRatingModel: [UserRatingModel] object to display
 * @param modifier: A modifier with default value [Modifier]
 */
@Composable
fun RatingRow(
        userRatingModel: UserRatingModel,
        modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = userRatingModel.userModel.picture),
            contentDescription = "user rating image",
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(width = 45.dp, height = 45.dp)
                .clip(CircleShape))
        Column {
            Text(text = "${userRatingModel.userModel.firstName} - ${userRatingModel.date.formatDate}")
            StarRating(ratingStars = userRatingModel.rating)
        }
    }
}

/**
 * A [Row] of star that display the nb of rating
 * @param ratingStars : Int that is the nb of star. Range from 0 to 5
 */
@Composable
fun StarRating(@IntRange(from = 0, to = 5) ratingStars: Int) {
    val positiveRating = 1..ratingStars
    val negativeRating = 1..(5 - ratingStars)
    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        positiveRating.forEach { _ ->
            Icon(
                painter = painterResource(id = R.drawable.star_24),
                contentDescription = stringResource(id = R.string.content_descrip_star_rating),
                tint = rating_positive)
        }
        negativeRating.forEach { _ ->
            Icon(
                painter = painterResource(id = R.drawable.star_24),
                contentDescription = stringResource(id = R.string.content_descrip_star_rating),
                tint = rating_negative)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RatingPreview() {
    CinematicsTheme {
        AverageRating(3, "4.4")
    }
}

@Preview(showBackground = true)
@Composable
fun StarRatingPreview() {
    CinematicsTheme {
        StarRating(3)
    }
}

@Preview(showBackground = true)
@Composable
fun RatingRowPreview() {
    CinematicsTheme {
        RatingRow(userRatingModel = userRatingModelLists[0])
    }
}

@Preview(showBackground = true)
@Composable
fun UserRatingsPreview() {
    CinematicsTheme {
        UserRatings(userRatingModelLists)
    }
}
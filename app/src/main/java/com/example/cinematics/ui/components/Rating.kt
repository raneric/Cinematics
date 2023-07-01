package com.example.cinematics.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cinematics.R
import com.example.cinematics.data.User
import com.example.cinematics.data.UserRating
import com.example.cinematics.data.userRatingList
import com.example.cinematics.ui.ui.theme.CinematicsTheme
import com.example.cinematics.ui.ui.theme.ratingTypo
import com.example.cinematics.ui.ui.theme.rating_positive
import com.example.cinematics.utils.formatedDate
import java.time.format.DateTimeFormatter

@Composable
fun Rating(ratingStars: Int,
           ratingValue: String,
           modifier: Modifier = Modifier) {
    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier) {
        Text(text = ratingValue,
             style = ratingTypo,
             color = MaterialTheme.colorScheme.onPrimary)
        StarRating(ratingStars)
    }
}

@Composable
fun UserRatings(ratingList: List<UserRating>,
                modifier: Modifier = Modifier) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        ratingList.forEach {
            RatingRow(userRating = it)
        }
    }
}

@Composable
fun RatingRow(userRating: UserRating,
              modifier: Modifier = Modifier) {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Image(painter = painterResource(id = userRating.user.picture),
              contentDescription = "user rating image",
              alignment = Alignment.Center,
              contentScale = ContentScale.Crop,
              modifier = modifier
                      .size(width = 45.dp, height = 45.dp)
                      .clip(CircleShape))
        Column {
            Text(text = "${userRating.user.name} - ${userRating.date.formatedDate}")
            StarRating(ratingStars = userRating.rating)
        }
    }
}

@Composable
fun StarRating(ratingStars: Int) {
    val positiveRating = 1..ratingStars
    val negativeRating = 1..(5 - ratingStars)
    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        positiveRating.forEach { _ ->
            Icon(painter = painterResource(id = R.drawable.star_24),
                 contentDescription = "",
                 tint = rating_positive)
        }
        negativeRating.forEach { _ ->
            Icon(painter = painterResource(id = R.drawable.star_24), contentDescription = "")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RatingPreview() {
    CinematicsTheme {
        Rating(3, "4.4")
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
        RatingRow(userRating = userRatingList[0])
    }
}

@Preview(showBackground = true)
@Composable
fun UserRatingsPreview() {
    CinematicsTheme {
        UserRatings(userRatingList)
    }
}
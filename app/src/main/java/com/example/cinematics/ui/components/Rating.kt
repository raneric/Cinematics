package com.example.cinematics.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cinematics.R
import com.example.cinematics.ui.ui.theme.CinematicsTheme
import com.example.cinematics.ui.ui.theme.ratingTypo
import com.example.cinematics.ui.ui.theme.rating_positive

@Composable
fun Rating(ratingStars: Int,ratingValue:String) {
    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = ratingValue, style = ratingTypo)
        StarRating(ratingStars)
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
        Rating(3,"4.4")
    }
}

@Preview(showBackground = true)
@Composable
fun StarRatingPreview() {
    StarRating(3)
}
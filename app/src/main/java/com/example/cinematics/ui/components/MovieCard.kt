package com.example.cinematics.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.cinematics.data.MovieModel
import com.example.cinematics.data.movieList
import com.example.cinematics.ui.ui.theme.CinematicsTheme

/**
 * A card composable that display movies information and using [Poster] as background
 * @param movie: [MovieModel] object to display
 * @param modifier: A modifier with default value [Modifier]
 */
@Composable
fun MovieCad(movie: MovieModel,
             modifier: Modifier = Modifier) {
    Box(modifier = modifier
            .fillMaxWidth()
            .height(263.dp)) {
        Poster(movie.picture)
        ConstraintLayout(modifier = Modifier.offset(x = 16.dp)) {
            val (tittle, movieInfo, genre, rating) = createRefs()

            Text(
                text = movie.title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.constrainAs(tittle) {
                    top.linkTo(parent.top, margin = 76.dp)
                }
            )
            Info(year = movie.year,
                 duration = movie.duration,
                 author = movie.author,
                 modifier = Modifier.constrainAs(movieInfo) {
                     top.linkTo(tittle.bottom, margin = 16.dp)
                 })
            Row(modifier = Modifier.constrainAs(genre) {
                top.linkTo(movieInfo.bottom, margin = 32.dp)
            }) {
                movie.genres.forEach {
                    Genre(text = it)
                }
            }
            Rating(ratingStars = movie.stars,
                   ratingValue = movie.ratingNote.toString(),
                   modifier = Modifier.constrainAs(rating) {
                       top.linkTo(genre.bottom, margin = 8.dp)
                   }
            )
        }
    }
}

/**
 * A card composable that is a variant of [MovieCad] with a rounded border
 * @param movie: [MovieModel] object to display
 * @param modifier: A modifier with default value [Modifier]
 */
@Composable
fun MovieCadRoundedBorder(movie: MovieModel,
                          modifier: Modifier = Modifier) {
    MovieCad(movie = movie,
             modifier = modifier.clip(MaterialTheme.shapes.small)
    )
}

/**
 * The card background photo with [GradientForeground] as gradient shade
 * @param picture : Drawable resources integer which is the ID of the picture resource to show
 * @param modifier: A modifier with default value [Modifier]
 */
@Composable
fun Poster(@DrawableRes picture: Int,
           modifier: Modifier = Modifier) {
    Box(modifier = modifier
            .fillMaxWidth()
            .height(263.dp)) {
        Image(painter = painterResource(id = picture),
              alignment = Alignment.Center,
              contentScale = ContentScale.Crop,
              contentDescription = "", modifier = Modifier.matchParentSize())
        GradientForeground(color = Color.Black, modifier = Modifier.height(263.dp))
    }
}

@Preview
@Composable
fun PosterPreview() {
    CinematicsTheme {
        Poster(movieList[0].picture)
    }
}

@Preview
@Composable
fun MovieCadPreview() {
    CinematicsTheme {
        MovieCad(movieList[1])
    }
}

@Preview
@Composable
fun MovieCadRoundedPreview() {
    CinematicsTheme {
        MovieCadRoundedBorder(movieList[1])
    }
}
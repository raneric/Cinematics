package com.sgg.cinematics.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.sgg.cinematics.R
import com.sgg.cinematics.data.fakeMovieList
import com.sgg.cinematics.data.model.MovieModel
import com.sgg.cinematics.ui.commonui.GenreRow
import com.sgg.cinematics.ui.commonui.GradientForeground
import com.sgg.cinematics.ui.ui.theme.CinematicsTheme
import com.sgg.cinematics.ui.ui.theme.md_theme_light_onPrimary

/**
 * A card composable that display movies information and using [Poster] as background
 *
 * @param movie: [MovieModel] object to display
 * @param modifier: A modifier with default value [Modifier]
 */
@Composable
fun MovieCad(
    movie: MovieModel,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.height(263.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        Poster(movie.picture)
        Column(
            modifier = Modifier
                .offset(x = dimensionResource(id = R.dimen.low_dp)),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = movie.title,
                style = MaterialTheme.typography.titleLarge,
                color = md_theme_light_onPrimary,
            )
            Spacer(modifier = Modifier.height(16.dp))
            MovieInfo(
                year = movie.year,
                duration = movie.displayedDuration,
                author = movie.author,
                compact = false,
            )
            Spacer(modifier = Modifier.height(32.dp))
            GenreRow(
                genres = movie.genres,
                compact = false,
            )
            Spacer(modifier = Modifier.height(16.dp))
            AverageRating(
                ratingStars = movie.stars,
                ratingValue = movie.ratingNote.toString(),
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


/**
 * A card composable that is a variant of [MovieCad] with a rounded border
 * @param movie: [MovieModel] object to display
 * @param modifier: A modifier with default value [Modifier]
 */
@Composable
fun MovieCadRoundedBorder(
    movie: MovieModel,
    modifier: Modifier = Modifier
) {
    MovieCad(
        movie = movie,
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
    )
}

@Composable
fun MovieCadRoundedBorderCompact(
    movie: MovieModel,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(width = 348.dp, height = 210.dp)
            .clip(MaterialTheme.shapes.small)
    ) {
        Poster(movie.picture)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .offset(x = dimensionResource(id = R.dimen.low_dp)),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                modifier = Modifier.widthIn(max = 200.dp),
                text = movie.title,
                style = MaterialTheme.typography.titleLarge,
                color = md_theme_light_onPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            MovieInfo(
                year = movie.year,
                duration = movie.displayedDuration,
                author = movie.author,
                compact = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            GenreRow(
                genres = movie.genres,
                compact = true
            )
            Spacer(modifier = Modifier.height(8.dp))
            AverageRating(
                ratingStars = movie.stars,
                ratingValue = movie.ratingNote.toString()
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

/**
 * The card background photo with [GradientForeground] as gradient shade
 * @param pictureUrl : Drawable resources integer which is the ID of the picture resource to show
 * @param modifier: A modifier with default value [Modifier]
 */
@Composable
fun Poster(
    pictureUrl: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(263.dp)
    ) {
        AsyncImage(
            model = pictureUrl,
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
            contentDescription = null, modifier = Modifier.matchParentSize()
        )
        GradientForeground(color = Color.Black, modifier = Modifier.height(263.dp))
    }
}

/**
 * A card composable that display movies information and using [Poster] as background
 * and using [ConstraintLayout] to display the information
 * @param movie: [MovieModel] object to display
 * @param modifier: A modifier with default value [Modifier]
 */
@Composable
@Deprecated("Please use MovieCard")
fun MovieCadWithCL(
    movie: MovieModel,
    modifier: Modifier = Modifier
) {

    Box(modifier = modifier.height(263.dp)) {
        Poster(movie.picture)
        ConstraintLayout(modifier = Modifier.offset(x = dimensionResource(id = R.dimen.low_dp))) {
            val (tittle, movieInfo, genre, rating) = createRefs()
            Text(text = movie.title,
                 style = MaterialTheme.typography.titleLarge,
                 color = md_theme_light_onPrimary,
                 modifier = Modifier.constrainAs(tittle) {
                     top.linkTo(anchor = parent.top, margin = 76.dp)
                 }
            )
            MovieInfo(year = movie.year,
                      duration = movie.displayedDuration,
                      author = movie.author,
                      compact = false,
                      modifier = Modifier.constrainAs(movieInfo) {
                          top.linkTo(tittle.bottom, margin = 16.dp)
                      })
            GenreRow(genres = movie.genres,
                     compact = false,
                     modifier = Modifier.constrainAs(genre) {
                         top.linkTo(movieInfo.bottom, margin = 32.dp)
                     })
            AverageRating(ratingStars = movie.stars,
                          ratingValue = movie.ratingNote.toString(),
                          modifier = Modifier.constrainAs(rating) {
                              top.linkTo(genre.bottom, margin = 8.dp)
                          }
            )
        }
    }
}

@Preview
@Composable
fun PosterPreview() {
    CinematicsTheme {
        Poster(fakeMovieList[0].picture)
    }
}

@Preview
@Composable
fun MovieCadPreview() {
    CinematicsTheme {
        MovieCadWithCL(fakeMovieList[0])
    }
}

@Preview
@Composable
private fun MovieCadColumnPreview() {
    CinematicsTheme {
        MovieCad(fakeMovieList[0])
    }
}

@Preview
@Composable
fun MovieCadRoundedPreview() {
    CinematicsTheme {
        MovieCadRoundedBorder(fakeMovieList[1])
    }
}

@Preview
@Composable
fun MovieCadRoundedBorderCompactPreview() {
    MovieCadRoundedBorderCompact(fakeMovieList[0])
}

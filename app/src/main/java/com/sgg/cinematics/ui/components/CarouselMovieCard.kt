package com.sgg.cinematics.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sgg.cinematics.R
import com.sgg.cinematics.data.fakeMovieList
import com.sgg.cinematics.data.model.MovieModel
import com.sgg.cinematics.ui.commonui.GenreRow
import com.sgg.cinematics.ui.ui.theme.CinematicsTheme
import com.sgg.cinematics.ui.ui.theme.md_theme_light_tertiary

@Composable
fun CarouselMovieCard(
    movie: MovieModel,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .width(280.dp)
            .height(600.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(
                horizontal = 16.dp, vertical = 16.dp
            )
        ) {
            AsyncImage(
                model = movie.picture,
                contentDescription = stringResource(id = R.string.content_descrip_movie_image),
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
            )
            Text(
                text = movie.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
            MovieInfo(
                year = movie.year,
                duration = movie.displayedDuration,
                author = movie.author,
                textColor = MaterialTheme.colorScheme.onSurface
            )
            GenreRow(
                genres = movie.genres,
                compact = true,
                color = md_theme_light_tertiary
            )
            AverageRating(
                ratingStars = movie.stars,
                ratingValue = movie.ratingNote.toString(),
                textColor = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview
@Composable
fun CarouselMovieCardPreview() {
    CinematicsTheme {
        CarouselMovieCard(fakeMovieList[0])
    }
}
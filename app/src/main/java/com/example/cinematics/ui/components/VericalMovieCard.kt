package com.example.cinematics.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cinematics.data.model.MovieModel
import com.example.cinematics.data.movieList
import com.example.cinematics.ui.ui.theme.CinematicsTheme
import com.example.cinematics.ui.ui.theme.md_theme_light_tertiary

@Composable
fun VerticalMovieCard(movie: MovieModel,
                      modifier: Modifier = Modifier) {
    Surface(shape = MaterialTheme.shapes.extraLarge,
            modifier = modifier
                    .width(280.dp)
                    .height(580.dp)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
               verticalArrangement = Arrangement.spacedBy(16.dp),
               modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp
               )) {
            Image(painter = painterResource(id = movie.picture),
                  contentDescription = "",
                  modifier = Modifier
                          .size(width = 238.dp, height = 343.dp)
                          .clip(MaterialTheme.shapes.large))
            Text(
                text = movie.title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
            MovieInfo(year = movie.year,
                      duration = movie.displayedDuration,
                      author = movie.author,
                      textColor = MaterialTheme.colorScheme.onSurface)
            GenreRow(genres = movie.genres,
                     compact = true,
                     color = md_theme_light_tertiary)
            AverageRating(ratingStars = movie.stars,
                          ratingValue = movie.ratingNote.toString(),
                          textColor = MaterialTheme.colorScheme.onSurface)
        }
    }
}

@Preview
@Composable
fun VerticalMovieCardPreview() {
    CinematicsTheme {
        VerticalMovieCard(movieList[0])
    }
}
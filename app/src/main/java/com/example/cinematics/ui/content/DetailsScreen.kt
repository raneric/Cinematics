package com.example.cinematics.ui.content

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.cinematics.R
import com.example.cinematics.data.MovieModel
import com.example.cinematics.data.movieList
import com.example.cinematics.data.userList
import com.example.cinematics.data.userRatingList
import com.example.cinematics.ui.components.BackNavigationFab
import com.example.cinematics.ui.components.Cast
import com.example.cinematics.ui.components.DETAILS_IMAGE_HEIGHT
import com.example.cinematics.ui.components.GenreRow
import com.example.cinematics.ui.components.GradientForeground
import com.example.cinematics.ui.components.MovieCadRoundedBorderCompact
import com.example.cinematics.ui.components.MovieDetailsImage
import com.example.cinematics.ui.components.MovieInfoDetails
import com.example.cinematics.ui.components.Overview
import com.example.cinematics.ui.components.StarRating
import com.example.cinematics.ui.components.UserRatings
import com.example.cinematics.ui.ui.theme.CinematicsTheme
import com.example.cinematics.ui.ui.theme.addToWatchButtonColor
import com.example.cinematics.ui.ui.theme.customButtonTextStyle
import com.example.cinematics.ui.ui.theme.md_theme_light_tertiary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(movie: MovieModel,
                  modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    Box {
        BackDrop(imageId = movie.picture)
        BackNavigationFab()
        DetailsLayout(moviePicture = { MovieDetailsImage(imageId = movie.picture) },
                      content = { DetailsContent(movie = movie) },
                      modifier = modifier.verticalScroll(scrollState))
    }
}

@Composable
fun BackDrop(@DrawableRes imageId: Int,
             modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Image(painter = painterResource(id = imageId),
              contentScale = ContentScale.Crop,
              modifier = Modifier.fillMaxSize(),
              contentDescription = "")
        GradientForeground(color = Color.Black, modifier = Modifier.fillMaxHeight())
    }
}

@Composable
fun DetailsLayout(
        moviePicture: @Composable () -> Unit,
        content: @Composable () -> Unit,
        modifier: Modifier = Modifier) {

    Layout(contents = listOf(moviePicture,
                             content),
           modifier = modifier) { (moviePictureMeasurable, contentMeasurable), constraint ->

        val moviePicturePlaceble = moviePictureMeasurable.first()
                .measure(constraint)
        val contentPlaceble = contentMeasurable.first()
                .measure(constraint)

        val contentY = contentPlaceble.height / 5
        val pictureYMargin = contentY - moviePicturePlaceble.height / 5
        val pictureXMargin = moviePicturePlaceble.width / 10
        val totalHeight = contentY + contentPlaceble.height

        layout(width = contentPlaceble.width, height = totalHeight) {
            contentPlaceble.place(x = 0, y = contentY)
            moviePicturePlaceble.place(x = pictureXMargin, y = pictureYMargin)
        }
    }
}

@Composable
fun DetailsContent(movie: MovieModel) {
    Surface(modifier = Modifier.fillMaxWidth(), shape = MaterialTheme.shapes.large) {
        ConstraintLayout(modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.low_dp))
        ) {
            val (movieInfo, genre, overview, cast, rating, userRatings, recommendation, button) = createRefs()

            Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.low_dp)),
                   modifier = Modifier
                           .constrainAs(movieInfo) {
                               top.linkTo(anchor = parent.top, margin = 24.dp)
                               absoluteLeft.linkTo(anchor = parent.absoluteLeft, margin = 200.dp)

                           }) {
                Text(text = movie.title,
                     modifier = Modifier.widthIn(max = 200.dp),
                     style = MaterialTheme.typography.titleLarge)
                MovieInfoDetails(
                    year = movie.year,
                    duration = movie.duration,
                    author = movie.author,
                    textColor = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            GenreRow(genres = movie.genres,
                     compact = true,
                     color = md_theme_light_tertiary,
                     modifier = Modifier.constrainAs(genre) {
                         top.linkTo(anchor = parent.top, margin = DETAILS_IMAGE_HEIGHT)
                         absoluteLeft.linkTo(anchor = parent.absoluteLeft)
                         absoluteRight.linkTo(anchor = parent.absoluteRight)
                     })

            DetailsSection(title = stringResource(id = R.string.txt_overview_section),
                           modifier = Modifier.constrainAs(overview) {
                               top.linkTo(anchor = genre.bottom, margin = SECTION_MARGIN)
                           }) {
                Overview(text = movie.overview)
            }

            DetailsSection(title = stringResource(id = R.string.txt_cast_section),
                           modifier = Modifier.constrainAs(cast) {
                               top.linkTo(overview.bottom)
                           }) {
                Cast(users = userList)
            }

            Row(horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(rating) {
                            top.linkTo(anchor = cast.bottom, margin = SECTION_MARGIN)
                        }) {
                Text(
                    text = stringResource(id = R.string.txt_rating_section, movie.ratingNote),
                    style = MaterialTheme.typography.titleMedium)
                StarRating(ratingStars = movie.stars)
            }

            UserRatings(ratingList = userRatingList, modifier = Modifier.constrainAs(userRatings) {
                top.linkTo(anchor = rating.bottom, margin = 32.dp)
            })

            DetailsSection(title = stringResource(id = R.string.txt_recomendation_section),
                           modifier = Modifier.constrainAs(recommendation) {
                               top.linkTo(anchor = userRatings.bottom, margin = SECTION_MARGIN)
                           }) {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(movieList) {
                        MovieCadRoundedBorderCompact(movie = it)
                    }
                }
            }

            Button(onClick = { /*TODO*/ },
                   shape = MaterialTheme.shapes.small,
                   colors = ButtonDefaults.buttonColors(containerColor = addToWatchButtonColor,
                                                        contentColor = Color.White),
                   modifier = Modifier
                           .fillMaxWidth()
                           .height(50.dp)
                           .constrainAs(button) {
                               top.linkTo(anchor = recommendation.bottom, margin = SECTION_MARGIN)
                               bottom.linkTo(anchor = parent.bottom, margin = 8.dp)
                           }) {
                Row {
                    Icon(painter = painterResource(id = R.drawable.icon_watch_list_24),
                         contentDescription = "")
                    Text(text = stringResource(id = R.string.txt_add_to_watch_btn),
                         style = customButtonTextStyle)
                }
            }

        }
    }
}

@Composable
fun DetailsSection(title: String,
                   modifier: Modifier = Modifier,
                   content: @Composable () -> Unit) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(text = title,
             style = MaterialTheme.typography.titleMedium,
             modifier = Modifier.widthIn(max = 200.dp))
        content()
    }
}

@Preview
@Composable
fun DetailsScreenPreview() {
    val movie = movieList[1]
    CinematicsTheme {
        DetailsScreen(movie)
    }
}

val SECTION_MARGIN = 32.dp
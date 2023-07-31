package com.example.cinematics.ui.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.cinematics.R
import com.example.cinematics.data.model.MovieModel
import com.example.cinematics.data.model.UserModel
import com.example.cinematics.data.movieList
import com.example.cinematics.data.userModelLists
import com.example.cinematics.data.userRatingModelLists
import com.example.cinematics.ui.components.AverageDetailRating
import com.example.cinematics.ui.commonui.BackDrop
import com.example.cinematics.ui.commonui.BackNavigationFab
import com.example.cinematics.ui.components.Cast
import com.example.cinematics.ui.commonui.CustomButton
import com.example.cinematics.ui.components.DETAILS_IMAGE_HEIGHT
import com.example.cinematics.ui.components.GenreRow
import com.example.cinematics.ui.components.MovieCadRoundedBorderCompact
import com.example.cinematics.ui.components.MovieDetailsImage
import com.example.cinematics.ui.components.MovieInfoDetails
import com.example.cinematics.ui.components.Overview
import com.example.cinematics.ui.components.UserRatings
import com.example.cinematics.ui.ui.theme.CinematicsTheme
import com.example.cinematics.ui.ui.theme.md_theme_light_tertiary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(movie: MovieModel,
                  isInWatchList: Boolean,
                  addOrRemoveWatchList: () -> Unit,
                  modifier: Modifier = Modifier,
                  onNavigateBack: () -> Unit) {
    val scrollState = rememberScrollState()
    Box {
        BackDrop(imageId = movie.picture)
        DetailsLayout(moviePicture = { MovieDetailsImage(imageId = movie.picture) },
                      content = {
                          DetailsContent(movie = movie,
                                         addOrRemoveWatchList = addOrRemoveWatchList,
                                         isInWatchList = isInWatchList)
                      },
                      modifier = modifier.verticalScroll(scrollState))
        BackNavigationFab(onNavigateBack = onNavigateBack)
    }
}

/**
 * This composable is a custom layout that place partially the [MovieDetailsImage] outside the content
 *
 * @param moviePicture : [MovieDetailsImage] composable that display movie image
 * @param content : Movie details content composable that display all movie info
 * @param modifier: A modifier with default value [Modifier]
 */
@Composable
fun DetailsLayout(
        moviePicture: @Composable () -> Unit,
        content: @Composable () -> Unit,
        modifier: Modifier = Modifier) {

    Layout(contents = listOf(moviePicture,
                             content),
           modifier = modifier) { (moviePictureMeasurable, contentMeasurable), constraint ->

        val moviePicturePlaceable = moviePictureMeasurable.first()
                .measure(constraint)
        val contentPlaceable = contentMeasurable.first()
                .measure(constraint)

        val contentY = contentPlaceable.height / 5
        val pictureYMargin = contentY - moviePicturePlaceable.height / 5
        val pictureXMargin = moviePicturePlaceable.width / 10
        val totalHeight = contentY + contentPlaceable.height

        layout(width = contentPlaceable.width, height = totalHeight) {
            contentPlaceable.place(x = 0, y = contentY)
            moviePicturePlaceable.place(x = pictureXMargin, y = pictureYMargin)
        }
    }
}

/**
 * This composable hold all screen section using constraint layout
 * @param isInWatchList : Boolean flag if movie is watched or not
 * @param addOrRemoveWatchList : button listener lambda
 * @param movie : [MovieModel] object to display
 * @param modifier: A modifier with default value [Modifier]
 */
@Composable
fun DetailsContent(movie: MovieModel,
                   isInWatchList: Boolean,
                   addOrRemoveWatchList: () -> Unit,
                   modifier: Modifier = Modifier) {
    Surface(modifier = modifier.fillMaxWidth(), shape = MaterialTheme.shapes.large) {
        ConstraintLayout(modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.low_dp))
        ) {
            val (movieInfo, genre, overview, cast, rating, userRatings, recommendation, button) = createRefs()

            MovieInfoSection(movie = movie, modifier = Modifier
                    .constrainAs(movieInfo) {
                        top.linkTo(anchor = parent.top, margin = 24.dp)
                        absoluteLeft.linkTo(anchor = parent.absoluteLeft, margin = 200.dp)

                    })

            GenreRow(genres = movie.genres,
                     compact = true,
                     color = md_theme_light_tertiary,
                     modifier = Modifier.constrainAs(genre) {
                         top.linkTo(anchor = parent.top, margin = DETAILS_IMAGE_HEIGHT)
                         absoluteLeft.linkTo(anchor = parent.absoluteLeft)
                         absoluteRight.linkTo(anchor = parent.absoluteRight)
                     })

            OverviewSection(text = movie.overview,
                            modifier = Modifier.constrainAs(overview) {
                                top.linkTo(anchor = genre.bottom, margin = SECTION_MARGIN)
                            })

            CastSection(userModelList = userModelLists,
                        modifier = Modifier.constrainAs(cast) {
                            top.linkTo(overview.bottom)
                        })

            AverageDetailRating(ratingStars = movie.stars,
                                ratingValue = movie.ratingNote,
                                modifier = Modifier
                                        .constrainAs(rating) {
                                            top.linkTo(anchor = cast.bottom,
                                                       margin = SECTION_MARGIN)
                                        })

            UserRatings(ratingList = userRatingModelLists,
                        modifier = Modifier.constrainAs(userRatings) {
                            top.linkTo(anchor = rating.bottom, margin = 32.dp)
                        })

            RecommendationSection(movieList = movieList,
                                  modifier = Modifier.constrainAs(recommendation) {
                                      top.linkTo(anchor = userRatings.bottom,
                                                 margin = SECTION_MARGIN)
                                  })

            CustomButton(inWatchList = isInWatchList,
                         modifier = Modifier
                                 .constrainAs(button) {
                                     top.linkTo(anchor = recommendation.bottom,
                                                margin = SECTION_MARGIN)
                                     bottom.linkTo(anchor = parent.bottom, margin = 8.dp)
                                 }) {
                addOrRemoveWatchList()
            }
        }
    }
}

@Composable
fun MovieInfoSection(movie: MovieModel,
                     modifier: Modifier = Modifier) {
    Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.low_dp)),
           modifier = modifier) {
        Text(text = movie.title,
             modifier = Modifier.widthIn(max = 200.dp),
             style = MaterialTheme.typography.titleLarge)
        MovieInfoDetails(
            year = movie.year,
            duration = movie.displayedDuration,
            author = movie.author,
            textColor = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
fun OverviewSection(text: String,
                    modifier: Modifier = Modifier) {
    DetailsSection(title = stringResource(id = R.string.txt_overview_section),
                   modifier = modifier) {
        Overview(text = text)
    }
}

@Composable
fun CastSection(userModelList: List<UserModel>,
                modifier: Modifier = Modifier) {
    DetailsSection(title = stringResource(id = R.string.txt_cast_section),
                   modifier = modifier) {
        Cast(userModels = userModelList)
    }
}

/**
 * This composable is a reusable composable that display each section in the movie details screen
 * @param title : String for the section title to display
 * @param modifier: A modifier with default value [Modifier]
 * @param content : composable argument that will be displayed in the section
 */
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

@Composable
fun RecommendationSection(movieList: List<MovieModel>,
                          modifier: Modifier = Modifier) {
    DetailsSection(title = stringResource(id = R.string.txt_recomendation_section),
                   modifier = modifier) {
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(movieList) {
                MovieCadRoundedBorderCompact(movie = it)
            }
        }
    }
}

@Preview
@Composable
fun DetailsScreenPreview() {
    val movie = movieList[1]
    CinematicsTheme {
        DetailsScreen(movie = movie, addOrRemoveWatchList = {}, isInWatchList = false) {

        }
    }
}

val SECTION_MARGIN = 32.dp
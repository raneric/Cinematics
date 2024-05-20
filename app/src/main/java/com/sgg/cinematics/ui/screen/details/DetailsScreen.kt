package com.sgg.cinematics.ui.screen.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.sgg.cinematics.R
import com.sgg.cinematics.data.model.MovieModel
import com.sgg.cinematics.data.model.UserModel
import com.sgg.cinematics.data.movieList
import com.sgg.cinematics.data.userModelLists
import com.sgg.cinematics.data.userRatingModelLists
import com.sgg.cinematics.ui.commonui.BackDrop
import com.sgg.cinematics.ui.commonui.BackNavigationFab
import com.sgg.cinematics.ui.commonui.BackNavigationTopBar
import com.sgg.cinematics.ui.commonui.CustomButton
import com.sgg.cinematics.ui.components.AverageDetailRating
import com.sgg.cinematics.ui.components.Cast
import com.sgg.cinematics.ui.components.DETAILS_IMAGE_HEIGHT
import com.sgg.cinematics.ui.components.GenreRow
import com.sgg.cinematics.ui.components.MovieCadRoundedBorderCompact
import com.sgg.cinematics.ui.components.MovieDetailsImage
import com.sgg.cinematics.ui.components.MovieInfoDetails
import com.sgg.cinematics.ui.components.Overview
import com.sgg.cinematics.ui.components.UserRatings
import com.sgg.cinematics.ui.ui.theme.CinematicsTheme
import com.sgg.cinematics.ui.ui.theme.md_theme_light_tertiary
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
        movie: MovieModel,
        isInWatchList: Boolean,
        addOrRemoveToWatchList: () -> Unit,
        onRecommendationItemClicked: (Int) -> Unit,
        modifier: Modifier = Modifier,
        onNavigateBack: () -> Unit
) {
    val scrollState = rememberScrollState()
    val shouldShowFab by remember {
        derivedStateOf { scrollState.value < 200 }
    }
    Box {
        BackDrop(imageUrl = movie.picture)
        DetailsLayout(
                moviePicture = { MovieDetailsImage(imageUrl = movie.picture) },
                content = {
                    DetailsContent(
                            movie = movie,
                            addOrRemoveWatchList = addOrRemoveToWatchList,
                            onRecommendationItemClicked = onRecommendationItemClicked,
                            isInWatchList = isInWatchList
                    )
                },
                modifier = modifier
                    .verticalScroll(scrollState)
        )
        
        AnimatedVisibility(visible = shouldShowFab,
                           enter = fadeIn(),
                           exit = fadeOut()) {
            BackNavigationFab(onNavigateBack = onNavigateBack)
        }

        AnimatedVisibility(visible = !shouldShowFab,
                           enter = fadeIn(),
                           exit = fadeOut()) {
            BackNavigationTopBar(onNavigateBack = onNavigateBack)
        }
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
        modifier: Modifier = Modifier
) {

    Layout(contents = listOf(
            moviePicture,
            content
    ),
           modifier = modifier
    ) { (moviePictureMeasurable, contentMeasurable), constraint ->

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
 * This composable hold all screen details section using constraint layout
 * @param isInWatchList : Boolean flag if movie is watched or not
 * @param addOrRemoveWatchList : add or remove button listener that lift state
 * @param movie : [MovieModel] object to display
 * @param modifier: A modifier with default value [Modifier]
 */
@Composable
fun DetailsContent(
        movie: MovieModel,
        isInWatchList: Boolean,
        addOrRemoveWatchList: () -> Unit,
        onRecommendationItemClicked: (Int) -> Unit,
        modifier: Modifier = Modifier
) {

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
                                        top.linkTo(
                                                anchor = cast.bottom,
                                                margin = SECTION_MARGIN
                                        )
                                    })

            UserRatings(ratingList = userRatingModelLists,
                        modifier = Modifier.constrainAs(userRatings) {
                            top.linkTo(anchor = rating.bottom, margin = 32.dp)
                        })

            RecommendationSection(movieList = movieList,
                                  onRecommendationItemClicked = onRecommendationItemClicked,
                                  modifier = Modifier.constrainAs(recommendation) {
                                      top.linkTo(
                                              anchor = userRatings.bottom,
                                              margin = SECTION_MARGIN
                                      )
                                  })

            CustomButton(inWatchList = isInWatchList,
                         modifier = Modifier
                             .testTag(stringResource(id = R.string.test_tag_button))
                             .constrainAs(button) {
                                 top.linkTo(
                                         anchor = recommendation.bottom,
                                         margin = SECTION_MARGIN
                                 )
                                 bottom.linkTo(anchor = parent.bottom, margin = 8.dp)
                             }) {
                addOrRemoveWatchList()
            }
        }
    }
}

@Composable
fun MovieInfoSection(
        movie: MovieModel,
        modifier: Modifier = Modifier
) {
    Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.low_dp)),
           modifier = modifier
    ) {
        Text(text = movie.title,
             modifier = Modifier.widthIn(max = 200.dp),
             style = MaterialTheme.typography.titleLarge
        )
        MovieInfoDetails(
                year = movie.year,
                duration = movie.displayedDuration,
                author = movie.author,
                textColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun OverviewSection(
        text: String,
        modifier: Modifier = Modifier
) {
    DetailsSection(
            title = stringResource(id = R.string.txt_overview_section),
            modifier = modifier
    ) {
        Overview(text = text)
    }
}

@Composable
fun CastSection(
        userModelList: ImmutableList<UserModel>,
        modifier: Modifier = Modifier
) {
    DetailsSection(
            title = stringResource(id = R.string.txt_cast_section),
            modifier = modifier
    ) {
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
fun DetailsSection(
        title: String,
        modifier: Modifier = Modifier,
        content: @Composable () -> Unit
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(text = title,
             style = MaterialTheme.typography.titleMedium,
             modifier = Modifier.widthIn(max = 200.dp)
        )
        content()
    }
}

@Composable
fun RecommendationSection(
        movieList: List<MovieModel>,
        onRecommendationItemClicked: (Int) -> Unit,
        modifier: Modifier = Modifier
) {
    DetailsSection(
            title = stringResource(id = R.string.txt_recomendation_section),
            modifier = modifier
    ) {
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(movieList) {
                MovieCadRoundedBorderCompact(movie = it,
                                             modifier = Modifier.clickable {
                                                 onRecommendationItemClicked(it.id)
                                             })
            }
        }
    }
}

@Preview
@Composable
fun DetailsScreenPreview() {
    val movie = movieList[1]
    CinematicsTheme {
        DetailsScreen(
                movie = movie,
                addOrRemoveToWatchList = {},
                onRecommendationItemClicked = {},
                isInWatchList = false
        ) {

        }
    }
}

val SECTION_MARGIN = 32.dp
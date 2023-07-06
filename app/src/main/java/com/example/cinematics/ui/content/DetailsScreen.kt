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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.cinematics.R
import com.example.cinematics.data.MovieModel
import com.example.cinematics.data.movieList
import com.example.cinematics.data.userList
import com.example.cinematics.data.userRatingList
import com.example.cinematics.ui.components.Cast
import com.example.cinematics.ui.components.GenreRow
import com.example.cinematics.ui.components.GradientForeground
import com.example.cinematics.ui.components.MovieCadRoundedBorderCompact
import com.example.cinematics.ui.components.MovieDetailsImage
import com.example.cinematics.ui.components.MovieInfo
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

    BottomSheetScaffold(
        sheetContent = { DetailsContent(movie) },
        sheetDragHandle = {},
        sheetContainerColor = MaterialTheme.colorScheme.surface,
        sheetPeekHeight = 600.dp) {
        BackDrop(movie.picture)

        FloatingActionButton(
            onClick = { /*TODO*/ },
            elevation = FloatingActionButtonDefaults.elevation(12.dp),
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            shape = CircleShape,
            modifier = Modifier.offset(x = 16.dp, y = 60.dp)) {
            Icon(painter = painterResource(id = R.drawable.arrow_back_24),
                 contentDescription = stringResource(id = R.string.content_descrip_back_fab))
        }
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
fun DetailsContent(movie: MovieModel) {

    Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
           verticalArrangement = Arrangement.spacedBy(32.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            MovieDetailsImage(imageId = movie.picture)

            //---------------------- Movie Info -----------------------------------------------
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(text = movie.title,
                     modifier = Modifier.widthIn(max = 200.dp),
                     style = MaterialTheme.typography.titleLarge)
                MovieInfo(
                    year = movie.year,
                    duration = movie.duration,
                    author = movie.author,
                    textColor = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        GenreRow(genres = movie.genres,
                 compact = true,
                 color = md_theme_light_tertiary,
                 modifier = Modifier.align(
                     Alignment.CenterHorizontally))
        //---------------------- Overview section -----------------------------------------------
        DetailsSection(title = stringResource(id = R.string.txt_overview_section)) {
            Overview(text = movie.overview)
        }

        //---------------------- Cast section -----------------------------------------------
        DetailsSection(
            title = stringResource(id = R.string.txt_cast_section),
            modifier = Modifier) {
            Cast(users = userList)
        }
        //---------------------- Ratings section -----------------------------------------------
        Row(horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(id = R.string.txt_rating_section, movie.ratingNote),
                style = MaterialTheme.typography.titleMedium)
            StarRating(ratingStars = movie.stars)
        }

        //---------------------- User Ratings section -----------------------------------------------
        UserRatings(ratingList = userRatingList)

        //---------------------- Recommendation  section -----------------------------------------------
        DetailsSection(title = stringResource(id = R.string.txt_recomendation_section)) {
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
                       .height(50.dp)) {
            Row {
                Icon(painter = painterResource(id = R.drawable.icon_watch_list_24),
                     contentDescription = "")
                Text(text = stringResource(id = R.string.txt_add_to_watch_btn),
                     style = customButtonTextStyle)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PrevT() {
    CinematicsTheme {
        DetailsContent(movieList[0])
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
    CinematicsTheme {
        DetailsScreen(movie = movieList[0])
    }
}
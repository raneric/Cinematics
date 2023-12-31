package com.sgg.cinematics.ui.screen.movieList

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.sgg.cinematics.R
import com.sgg.cinematics.data.model.MovieModel
import com.sgg.cinematics.data.movieList
import com.sgg.cinematics.ui.commonui.BackDrop
import com.sgg.cinematics.ui.components.EmptyListScreen
import com.sgg.cinematics.ui.components.MovieCad
import com.sgg.cinematics.ui.components.VerticalMovieCard
import com.sgg.cinematics.ui.ui.theme.CinematicsTheme
import com.sgg.cinematics.utils.MovieListUiMode
import com.sgg.cinematics.utils.navigateToDetailsScreen

/**
 * Main movie list composable that display movie list depending on [MovieListUiMode] and [WindowWidthSizeClass]
 *
 *
 * @param movieListUiMode : display mode [MovieListUiMode.ListView] or [MovieListUiMode.CarouselView]
 * @param movieList : List of the movie to display
 * @param navController
 * @param windowsWidthSizeClass
 * @param modifier
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MovieListScreen(
        movieListUiMode: MovieListUiMode,
        modifier: Modifier = Modifier,
        movieList: List<MovieModel>,
        navController: NavHostController,
        windowsWidthSizeClass: WindowWidthSizeClass,
        onItemClicked: (Int) -> Unit
) {
    if (windowsWidthSizeClass == WindowWidthSizeClass.Compact) {
        AnimatedVisibility(
            visible = movieListUiMode is MovieListUiMode.ListView,
            enter = scaleIn(),
            exit = fadeOut()) {
            VerticalMovieListScreen(
                movieList = movieList,
                modifier = modifier.testTag(movieListUiMode.testTag)) { movieId ->
                navigateToDetailsScreen(movieId = movieId, navController = navController)
                onItemClicked(movieId)
            }
        }
        AnimatedVisibility(
            visible = movieListUiMode is MovieListUiMode.CarouselView,
            enter = scaleIn(),
            exit = fadeOut()) {
            HorizontalMovieListScreen(
                movieList = movieList,
                modifier = modifier.testTag(movieListUiMode.testTag)) { movieId ->
                navigateToDetailsScreen(movieId = movieId, navController = navController)
                onItemClicked(movieId)
            }
        }
    } else {
        AnimatedVisibility(
            visible = movieListUiMode is MovieListUiMode.CarouselView,
            enter = scaleIn(),
            exit = fadeOut()) {
            GridMovieListScreen(movieList = movieList) { movieId ->
                navigateToDetailsScreen(movieId = movieId, navController = navController)
                onItemClicked(movieId)
            }
        }
    }
}


/**
 * This composable use [LazyColumn] to display movie list vertically with [MovieCad] composable
 *
 * @param movieList : List of the movie to display in the lazyColumn
 * @param modifier: A modifier with default value [Modifier]
 * @param onItemClicked : lambda function that will trigger the movie clicked by user and open
 * the detail screen
 */
@Composable
fun VerticalMovieListScreen(
        movieList: List<MovieModel>,
        modifier: Modifier = Modifier,
        onItemClicked: (Int) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(movieList) {
            MovieCad(movie = it,
                     modifier = Modifier
                             .testTag(stringResource(id = R.string.test_tag_card))
                             .clickable {
                                 onItemClicked(it.id)
                             })
        }
    }
}

/**
 * This composable use [HorizontalPager] to display movie list vertically with
 *  [VerticalMovieCard] composable
 *
 * @param movieList : List of the movie to display in the lazyColumn
 * @param modifier: A modifier with default value [Modifier]
 * @param onItemClicked : lambda function that will trigger the movie clicked by user and open
 * the detail screen
 */
@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun HorizontalMovieListScreen(
        movieList: List<MovieModel>,
        modifier: Modifier = Modifier,
        onItemClicked: (Int) -> Unit
) {

    val pageSate = rememberPagerState(pageCount = { movieList.size })

    if (movieList.isEmpty()) {
        EmptyListScreen()
    } else {
        Box(contentAlignment = Alignment.Center, modifier = modifier) {
            AnimatedContent(targetState = pageSate.currentPage,
                            label = stringResource(id = R.string.label_backdrop_animation),
                            transitionSpec = {
                                (fadeIn() + scaleIn()).togetherWith(fadeOut())
                            }) {
                BackDrop(imageUrl = movieList[it].picture)
            }
            HorizontalPager(
                state = pageSate,
                contentPadding = PaddingValues(horizontal = 100.dp),
                pageSize = PageSize.Fixed(300.dp),
                pageSpacing = 8.dp) {
                VerticalMovieCard(movie = movieList[it],
                                  modifier = Modifier
                                          .testTag(stringResource(id = R.string.test_tag_card))
                                          .clickable {
                                              onItemClicked(movieList[it].id)
                                          })
            }
        }
    }
}

@Composable
fun GridMovieListScreen(
        movieList: List<MovieModel>,
        modifier: Modifier = Modifier,
        onItemClicked: (Int) -> Unit
) {
    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 400.dp)) {
        items(movieList) {
            MovieCad(movie = it,
                     modifier = Modifier
                             .testTag(stringResource(id = R.string.test_tag_card))
                             .clickable {
                                 onItemClicked(it.id)
                             })
        }
    }
}

@Preview
@Composable
fun MovieListScreenPreview() {
    VerticalMovieListScreen(movieList) {

    }
}


@Preview(showBackground = true)
@Composable
fun EmptyMovieListScreenPreview() {
    CinematicsTheme {
        VerticalMovieListScreen(emptyList()) {

        }
    }
}

@Preview
@Composable
fun HorizontalMovieListScreenPreview() {
    CinematicsTheme {
        HorizontalMovieListScreen(movieList) {

        }
    }
}

@Preview(widthDp = 1360, heightDp = 780)
@Composable
fun GridMovieListScreenPreview() {
    CinematicsTheme {
        GridMovieListScreen(movieList = movieList, onItemClicked = {})
    }
}
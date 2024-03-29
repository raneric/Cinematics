package com.sgg.cinematics.ui.screen.movieList

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
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
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.immutableListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch

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
    modifier: Modifier = Modifier,
    movieListUiMode: MovieListUiMode,
    movieList: ImmutableList<MovieModel>,
    navController: NavHostController,
    windowsWidthSizeClass: WindowWidthSizeClass,
) {
    if (windowsWidthSizeClass == WindowWidthSizeClass.Compact) {
        AnimatedVisibility(
                visible = movieListUiMode is MovieListUiMode.ListView,
                enter = scaleIn(),
                exit = fadeOut()
        ) {
            VerticalMovieListScreen(
                    movieList = movieList,
                    modifier = modifier.testTag(movieListUiMode.testTag)
            ) { movieId ->
                navigateToDetailsScreen(movieId = movieId, navController = navController)
            }
        }
        AnimatedVisibility(
                visible = movieListUiMode is MovieListUiMode.CarouselView,
                enter = scaleIn(),
                exit = fadeOut()
        ) {
            HorizontalMovieListScreen(
                    movieList = movieList,
                    modifier = modifier.testTag(movieListUiMode.testTag)
            ) { movieId ->
                navigateToDetailsScreen(movieId = movieId, navController = navController)
            }
        }
    } else {
        GridMovieListScreen(movieList = movieList) { movieId ->
            navigateToDetailsScreen(movieId = movieId, navController = navController)
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
    movieList: ImmutableList<MovieModel>,
    modifier: Modifier = Modifier,
    onItemClicked: (Int) -> Unit
) {
    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    Box {
        LazyColumn(state = scrollState,
                   modifier = modifier
        ) {
            items(movieList) {
                MovieCad(movie = it,
                         modifier = Modifier
                             .testTag(stringResource(id = R.string.test_tag_card))
                             .clickable {
                                 onItemClicked(it.id)
                             })
            }
        }
        AnimatedVisibility(modifier = Modifier.align(Alignment.BottomCenter),
                           visible = scrollState.firstVisibleItemIndex != 0,
                           enter = fadeIn() + scaleIn(),
                           exit = fadeOut() + scaleOut()
        ) {
            ScrollUpButton {
                scope.launch {
                    scrollState.scrollToItem(0)
                }
            }
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
    movieList: ImmutableList<MovieModel>,
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
                    pageSpacing = 8.dp
            ) {
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
    movieList: ImmutableList<MovieModel>,
    modifier: Modifier = Modifier,
    onItemClicked: (Int) -> Unit
) {
    val scrollState = rememberLazyGridState()
    val scope = rememberCoroutineScope()
    Box {
        LazyVerticalGrid(state = scrollState,
                         columns = GridCells.Adaptive(minSize = 400.dp)
        ) {
            items(movieList) {
                MovieCad(movie = it,
                         modifier = Modifier
                             .testTag(stringResource(id = R.string.test_tag_card))
                             .clickable {
                                 onItemClicked(it.id)
                             })
            }
        }
        AnimatedVisibility(modifier = Modifier.align(Alignment.BottomCenter),
                           visible = scrollState.firstVisibleItemIndex != 0,
                           enter = fadeIn() + scaleIn(),
                           exit = fadeOut() + scaleOut()
        ) {
            ScrollUpButton {
                scope.launch {
                    scrollState.scrollToItem(0)
                }
            }
        }
    }

}

@Composable
fun ScrollUpButton(onClick: () -> Unit) {
    Button(onClick = {
        onClick()
    }) {
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            Icon(painter = painterResource(id = R.drawable.icon_arrow_circle_up_24px),
                 contentDescription = ""
            )
            Text(text = stringResource(id = R.string.txt_scroll_up))
        }
    }
}

@Preview
@Composable
fun MovieListScreenPreview() {
    VerticalMovieListScreen(movieList.toImmutableList()) {

    }
}


@Preview(showBackground = true)
@Composable
fun EmptyMovieListScreenPreview() {
    CinematicsTheme {
        VerticalMovieListScreen(immutableListOf()) {

        }
    }
}

@Preview
@Composable
fun HorizontalMovieListScreenPreview() {
    CinematicsTheme {
        HorizontalMovieListScreen(movieList.toImmutableList()) {

        }
    }
}

@Preview(widthDp = 1360, heightDp = 780)
@Composable
fun GridMovieListScreenPreview() {
    CinematicsTheme {
        GridMovieListScreen(movieList = movieList.toImmutableList(), onItemClicked = {})
    }
}
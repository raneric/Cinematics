package com.sgg.cinematics.ui.screen.movieList

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sgg.cinematics.R
import com.sgg.cinematics.data.fakeMovieList
import com.sgg.cinematics.data.model.MovieModel
import com.sgg.cinematics.ui.CinematicsAppState
import com.sgg.cinematics.ui.commonui.MovieDisplaySwitchFab
import com.sgg.cinematics.ui.components.BackDrop
import com.sgg.cinematics.ui.components.CarouselMovieCard
import com.sgg.cinematics.ui.components.EmptyListScreen
import com.sgg.cinematics.ui.components.MovieCad
import com.sgg.cinematics.ui.components.MovieCadWithCL
import com.sgg.cinematics.ui.screen.details.navigateToDetailsScreen
import com.sgg.cinematics.ui.ui.theme.CinematicsTheme
import com.sgg.cinematics.utils.MovieListFilter
import com.sgg.cinematics.utils.MovieListUiMode
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch

const val SHARD_ANIM_KEY = "shared_anim_key"

/**
 * Main movie list composable that display movie list depending on [MovieListUiMode] and [WindowWidthSizeClass]
 *
 * @param movieListUiMode : display mode [MovieListUiMode.ListView] or [MovieListUiMode.CarouselView]
 * @param movieList : List of the movie to display
 * @param navController
 * @param windowsWidthSizeClass
 * @param modifier
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MovieListScreen(
    cinematicsAppState: CinematicsAppState,
    movieList: ImmutableList<MovieModel>,
    modifier: Modifier = Modifier,
    movieListViewModel: MovieListViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    val movieListUiMode by movieListViewModel.uiListMode.collectAsStateWithLifecycle(initialValue = MovieListUiMode.ListView)

    val activeDestinationRoute = cinematicsAppState.activeNavItem.destination.route

    var filterDialogVisibility by remember {
        mutableStateOf(false)
    }
    var selectedFilter by remember {
        mutableStateOf(MovieListFilter.TRENDING)
    }

    if (cinematicsAppState.windowWidthSizeClass == WindowWidthSizeClass.Compact) {
        Box(modifier = modifier.fillMaxHeight()) {
            SharedTransitionLayout {
                AnimatedContent(
                    targetState = movieListUiMode,
                    label = "Movie list animation"
                ) { uiMode ->
                    when (uiMode) {
                        MovieListUiMode.ListView -> {
                            ColumnMovieListScreen(
                                movieList = movieList.toImmutableList(),
                                animatedVisibilityScope = this@AnimatedContent,
                                sharedTransitionScope = this@SharedTransitionLayout,
                                modifier = Modifier.testTag(movieListUiMode.testTag)
                            ) { movieId ->
                                cinematicsAppState.navController.navigateToDetailsScreen(movieId = movieId)
                            }
                        }

                        MovieListUiMode.CarouselView -> {
                            CarouselMovieListScreen(
                                movieList = movieList.toImmutableList(),
                                animatedVisibilityScope = this@AnimatedContent,
                                sharedTransitionScope = this@SharedTransitionLayout,
                                modifier = Modifier.testTag(movieListUiMode.testTag)
                            ) { movieId ->
                                cinematicsAppState.navController.navigateToDetailsScreen(movieId = movieId)
                            }
                        }
                    }
                }
            }

            AnimatedVisibility(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                visible = movieList.isNotEmpty(),
                enter = scaleIn(
                    animationSpec = spring(
                        dampingRatio =
                        Spring.DampingRatioMediumBouncy
                    )
                )
            ) {
                MovieDisplaySwitchFab(
                    fabIcon = movieListUiMode.fabIcon
                ) {
                    coroutineScope.launch {
                        movieListViewModel.switchListViewMode(movieListUiMode.switch())
                    }
                }
            }

            FilterButton(
                onClick = { filterDialogVisibility = true },
                modifier = Modifier.align(Alignment.TopEnd)
            )

            AnimatedVisibility(filterDialogVisibility) {
                FilterDialog(onDismissRequest = { filterDialogVisibility = false },
                             selectedOption = selectedFilter,
                             onOptionSelected = {
                                 selectedFilter = it
                                 movieListViewModel.loadRequiredMovieList(
                                     destinationRoute = activeDestinationRoute,
                                     filter = it
                                 )
                             })
            }
        }
    } else {
        Box {
            GridMovieListScreen(movieList = movieList.toImmutableList()) { movieId ->
                cinematicsAppState.navController.navigateToDetailsScreen(movieId = movieId)
            }
            FilterButton(
                onClick = {},
                modifier = Modifier.align(Alignment.TopEnd)
            )
        }
    }

}

/**
 * This composable use [LazyColumn] to display movie list vertically with [MovieCadWithCL] composable
 *
 * @param movieList : List of the movie to display in the lazyColumn
 * @param modifier: A modifier with default value [Modifier]
 * @param onItemClicked : lambda function that will trigger the movie clicked by user and open
 * the detail screen
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ColumnMovieListScreen(
    movieList: ImmutableList<MovieModel>,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
    onItemClicked: (Int) -> Unit
) {
    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val showScrollButton by remember {
        derivedStateOf {
            scrollState.firstVisibleItemIndex != 0
        }
    }

    Box {
        LazyColumn(
            state = scrollState,
            modifier = modifier
        ) {

            itemsIndexed(items = movieList) { index, movie ->
                with(sharedTransitionScope) {
                    MovieCad(movie = movie,
                             modifier = Modifier
                                 .sharedBounds(
                                     sharedContentState = rememberSharedContentState(key = "${SHARD_ANIM_KEY}_${movie.id}"),
                                     animatedVisibilityScope = animatedVisibilityScope,
                                     enter = slideInAndFadeInByIndex(index),
                                     exit = slideOutAndFadeOutByIndex(index)
                                 )
                                 .testTag(stringResource(id = R.string.test_tag_card))
                                 .clickable {
                                     onItemClicked(movie.id)
                                 })
                }
            }
        }
        AnimatedVisibility(
            modifier = Modifier.align(Alignment.BottomCenter),
            visible = showScrollButton,
            enter = slideInVertically(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
                    + fadeIn(),
            exit = slideOutVertically() + fadeOut()
        ) {
            ScrollUpButton {
                scope.launch {
                    scrollState.animateScrollToItem(0)
                }
            }
        }
    }
}

/**
 * This composable use [HorizontalPager] to display movie list vertically with
 *  [CarouselMovieCard] composable
 *
 * @param movieList : List of the movie to display in the lazyColumn
 * @param modifier: A modifier with default value [Modifier]
 * @param onItemClicked : lambda function that will trigger the movie clicked by user and open
 * the detail screen
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun CarouselMovieListScreen(
    movieList: ImmutableList<MovieModel>,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
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
                                (fadeIn() + scaleIn(initialScale = 2f)).togetherWith(fadeOut())
                            }) {
                BackDrop(imageUrl = movieList[it].picture)
            }
            HorizontalPager(
                state = pageSate,
                contentPadding = PaddingValues(horizontal = 100.dp),
                beyondViewportPageCount = 2,
                pageSize = PageSize.Fixed(300.dp),
                pageSpacing = 8.dp
            ) {
                with(sharedTransitionScope) {
                    CarouselMovieCard(movie = movieList[it],
                                      modifier = Modifier
                                          .sharedBounds(
                                              sharedContentState = rememberSharedContentState(
                                                  key = "${SHARD_ANIM_KEY}_${movieList[it].id}"
                                              ),
                                              animatedVisibilityScope = animatedVisibilityScope
                                          )
                                          .testTag(stringResource(id = R.string.test_tag_card))
                                          .clickable {
                                              onItemClicked(movieList[it].id)
                                          })
                }
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
    Box(modifier = modifier) {
        LazyVerticalGrid(
            state = scrollState,
            columns = GridCells.Adaptive(minSize = 400.dp)
        ) {
            items(movieList) {
                MovieCadWithCL(movie = it,
                               modifier = Modifier
                                   .testTag(stringResource(id = R.string.test_tag_card))
                                   .clickable {
                                       onItemClicked(it.id)
                                   })
            }
        }
        AnimatedVisibility(
            modifier = Modifier.align(Alignment.BottomCenter),
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
            Icon(
                painter = painterResource(id = R.drawable.icon_arrow_circle_up_24px),
                contentDescription = ""
            )
            Text(text = stringResource(id = R.string.txt_scroll_up))
        }
    }
}

@Composable
fun FilterDialog(
    onDismissRequest: () -> Unit,
    onOptionSelected: (MovieListFilter) -> Unit,
    selectedOption: MovieListFilter,
    modifier: Modifier = Modifier
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(shape = MaterialTheme.shapes.medium) {
            Column(
                modifier = modifier
                    .selectableGroup()
                    .widthIn(max = 170.dp)
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    )
            ) {
                Text(
                    text = "Filter by",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.headlineSmall
                )
                HorizontalDivider()
                MovieListFilter.values()
                    .forEach { filter ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(selected = filter == selectedOption,
                                        onClick = { onOptionSelected(filter) })
                            Text(
                                text = stringResource(id = filter.labelStringResId),
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }
            }
        }
    }
}

@Composable
fun FilterButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = R.drawable.icon_filter_list_24),
            contentDescription = "",
            modifier = Modifier.size(32.dp)
        )
    }
}

@Preview
@Composable
fun MovieListScreenPreview() {
    /* VerticalMovieListScreen(movieList = fakeMovieList.toImmutableList()) {

     }*/
}


@Preview(showBackground = true)
@Composable
fun EmptyMovieListScreenPreview() {
    CinematicsTheme {
        /*     VerticalMovieListScreen(fakeMovieList.toImmutableList()) {

             }*/
    }
}

@Preview
@Composable
fun HorizontalMovieListScreenPreview() {
    CinematicsTheme {
        /*  HorizontalMovieListScreen(fakeMovieList.toImmutableList()) {

          }*/
    }
}

@Preview(widthDp = 1360, heightDp = 780)
@Composable
fun GridMovieListScreenPreview() {
    CinematicsTheme {
        GridMovieListScreen(movieList = fakeMovieList.toImmutableList(), onItemClicked = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun FilterDialogPreview() {
    CinematicsTheme {
        FilterDialog(onDismissRequest = {},
                     selectedOption = MovieListFilter.TRENDING,
                     onOptionSelected = {})
    }
}

@Stable
private fun slideInAndFadeInByIndex(index: Int): EnterTransition {
    val animationSpec: FiniteAnimationSpec<IntOffset> = spring(
        dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = Spring.StiffnessLow
    )
    return if (index % 2 == 0) {
        slideInHorizontally(animationSpec = animationSpec) { width -> width / 2 }
    } else {
        slideInHorizontally(animationSpec = animationSpec)
    } + fadeIn()
}

private fun slideOutAndFadeOutByIndex(index: Int): ExitTransition {
    val animationSpec: FiniteAnimationSpec<IntOffset> = tween(easing = LinearOutSlowInEasing)
    return if (index % 2 == 0) {
        slideOutHorizontally(animationSpec = animationSpec) { width -> width }
    } else {
        slideOutHorizontally(animationSpec = animationSpec) { width -> -width }
    } + fadeOut()
}
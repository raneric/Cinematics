package com.example.cinematics.ui.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cinematics.data.model.MovieModel
import com.example.cinematics.data.movieList
import com.example.cinematics.ui.commonui.BackDrop
import com.example.cinematics.ui.components.MovieCad
import com.example.cinematics.ui.components.VerticalMovieCard

@Composable
fun VerticalMovieListScreen(movieList: List<MovieModel>,
                            modifier: Modifier = Modifier,
                            onItemClicked: (Int) -> Unit) {

    LazyColumn(modifier = modifier) {
        items(movieList) {
            MovieCad(movie = it, modifier = Modifier.clickable {
                onItemClicked(it.id)
            })
        }
    }
}


@Composable
fun HorizontalMovieListScreen(movieList: List<MovieModel>,
                              modifier: Modifier = Modifier,
                              onItemClicked: (Int) -> Unit) {

    Box(contentAlignment = Alignment.Center) {
        BackDrop(imageId = movieList[0].picture)
        LazyRow(modifier = modifier,
                horizontalArrangement = Arrangement.spacedBy(32.dp)) {
            items(movieList) {
                VerticalMovieCard(movie = it, modifier = Modifier.clickable {
                    onItemClicked(it.id)
                })
            }
        }
    }
}

@Preview
@Composable
fun MovieListScreenPreview() {
    VerticalMovieListScreen(movieList) {

    }
}

@Preview
@Composable
fun HorizontalMovieListScreenPreview() {
    HorizontalMovieListScreen(movieList) {

    }
}
package com.example.cinematics.ui.content

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.cinematics.data.MovieModel
import com.example.cinematics.data.movieList
import com.example.cinematics.ui.components.MovieCad

@Composable
fun MovieListScreen(movieList: List<MovieModel>,
                    modifier: Modifier = Modifier) {

    LazyColumn(modifier = modifier) {
        items(movieList) {
            MovieCad(movie = it)
        }
    }
}

@Preview
@Composable
fun MovieListScreenPreview() {
    MovieListScreen(movieList)
}
package com.example.cinematics.ui.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.cinematics.data.model.MovieModel
import com.example.cinematics.data.movieList
import com.example.cinematics.ui.components.MovieCad
import kotlinx.coroutines.flow.Flow

@Composable
fun MovieListScreen(movieList: List<MovieModel>,
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

@Preview
@Composable
fun MovieListScreenPreview() {
    MovieListScreen(movieList) {

    }
}
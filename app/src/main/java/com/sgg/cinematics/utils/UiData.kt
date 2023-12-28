package com.sgg.cinematics.utils

import com.sgg.cinematics.data.model.MovieModel
import kotlinx.coroutines.flow.Flow

sealed class UiData {
    data class ListScreenData(val movieList: Flow<List<MovieModel>>) : UiData()

    data class DetailScreenData(val movie: MovieModel) : UiData()
}

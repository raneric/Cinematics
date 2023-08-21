package com.example.cinematics.utils

import com.example.cinematics.data.model.MovieModel

data class UiData(val trendingList: List<MovieModel>,
                  val topRatedList: List<MovieModel>,
                  val watchList: List<MovieModel>)

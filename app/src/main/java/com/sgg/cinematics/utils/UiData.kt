package com.sgg.cinematics.utils

import com.sgg.cinematics.data.model.MovieModel

data class UiData(val trendingList: List<MovieModel>,
                  val topRatedList: List<MovieModel>,
                  val watchList: List<MovieModel>)

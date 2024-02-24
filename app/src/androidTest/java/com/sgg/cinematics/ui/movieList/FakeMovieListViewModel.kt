package com.sgg.cinematics.ui.movieList

import com.sgg.cinematics.data.repository.MovieRepository
import com.sgg.cinematics.data.repository.impl.UiStatePreferencesRepositoryImpl
import com.sgg.cinematics.service.AuthService
import com.sgg.cinematics.ui.screen.movieList.MovieListViewModel

class FakeMovieListViewModel(
        private val repository: MovieRepository,
        private val authService: AuthService,
        private val uiStateRepository: UiStatePreferencesRepositoryImpl
) : MovieListViewModel(
    repository = repository,
    authService = authService,
    uiStateRepository = uiStateRepository) {

}
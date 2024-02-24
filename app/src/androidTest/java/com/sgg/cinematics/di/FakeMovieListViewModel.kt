package com.sgg.cinematics.di

import com.sgg.cinematics.data.TestMovieRepository
import com.sgg.cinematics.data.repository.impl.UiStatePreferencesRepositoryImpl
import com.sgg.cinematics.data.testDataStore
import com.sgg.cinematics.service.FakeAuthService
import com.sgg.cinematics.ui.movieList.FakeMovieListViewModel
import com.sgg.cinematics.ui.screen.movieList.MovieListViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object FakeMovieListViewModel {
    @Provides
    fun provideMovieListViewModel(): MovieListViewModel {
        val fakeRepo = TestMovieRepository()
        val authService = FakeAuthService()
        val fakeUiStateRepo = UiStatePreferencesRepositoryImpl(testDataStore)
        return FakeMovieListViewModel(fakeRepo, authService, fakeUiStateRepo)
    }
}
package com.sgg.cinematics.di

import com.sgg.cinematics.data.repository.FakeMovieRepository
import com.sgg.cinematics.data.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(fakeMovieRepository: FakeMovieRepository): MovieRepository

}
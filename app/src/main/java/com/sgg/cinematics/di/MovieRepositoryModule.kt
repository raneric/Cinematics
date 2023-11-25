package com.sgg.cinematics.di

import com.sgg.cinematics.data.repository.MovieRepository
import com.sgg.cinematics.data.repository.impl.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MovieRepositoryModule {

    @Binds
    abstract fun provideRepository(movieRepository: MovieRepositoryImpl): MovieRepository

}
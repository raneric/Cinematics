package com.example.cinematics.di

import com.example.cinematics.data.TestMovieRepository
import com.example.cinematics.data.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(components = [SingletonComponent::class],
               replaces = [RepositoryModule::class])
abstract class TestRepositoryModule {

    @Binds
    abstract fun provideTestRepository(testMovieRepository: TestMovieRepository): MovieRepository
}
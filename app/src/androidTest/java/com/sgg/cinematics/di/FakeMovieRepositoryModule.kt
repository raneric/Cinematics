package com.sgg.cinematics.di

import com.sgg.cinematics.data.TestMovieRepository
import com.sgg.cinematics.data.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn


@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [MovieRepositoryModule::class]
)
abstract class FakeMovieRepositoryModule {
    @Binds
    abstract fun provideRepository(movieRepository: TestMovieRepository): MovieRepository
}

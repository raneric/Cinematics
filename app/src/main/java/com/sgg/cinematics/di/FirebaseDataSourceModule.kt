package com.sgg.cinematics.di

import com.sgg.cinematics.data.datasource.MovieDataSource
import com.sgg.cinematics.data.datasource.impl.MovieDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class FirebaseDataSourceModule {
    
    @Binds
    abstract fun provideMovieDataSource(movieDataSource: MovieDataSourceImpl): MovieDataSource
}
package com.sgg.cinematics.di

import com.sgg.cinematics.data.repository.PreferenceRepository
import com.sgg.cinematics.data.repository.impl.UiStatePreferencesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class PreferenceRepositoryModule {

    @Binds
    abstract fun providePreferenceRepository(preferenceRepository: UiStatePreferencesRepositoryImpl): PreferenceRepository
}
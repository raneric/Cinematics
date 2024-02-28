package com.sgg.cinematics.di

import com.sgg.cinematics.data.FakePreferenceRepository
import com.sgg.cinematics.data.repository.PreferenceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [PreferenceRepositoryModule::class])
abstract class FakePrefRepositoryModule {
    @Binds
    abstract fun providePreferenceRepository(preferenceRepository: FakePreferenceRepository): PreferenceRepository
}
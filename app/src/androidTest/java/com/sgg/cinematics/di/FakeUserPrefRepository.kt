package com.sgg.cinematics.di

import com.sgg.cinematics.data.testDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataStoreModule::class])
@Module
object FakeDateStoreModule {

    @Singleton
    @Provides
    fun provideDataStore() = testDataStore
}
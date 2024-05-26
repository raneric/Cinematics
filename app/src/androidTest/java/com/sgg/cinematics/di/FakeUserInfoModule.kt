package com.sgg.cinematics.di

import com.sgg.cinematics.data.FakeUserInfoRepository
import com.sgg.cinematics.data.repository.UserInfoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
        components = [SingletonComponent::class],
        replaces = [UserInfoRepositoryModule::class])
abstract class FakeUserInfoModule {
    @Binds
    abstract fun provideUerRepository(userInfoRepository: FakeUserInfoRepository): UserInfoRepository
}
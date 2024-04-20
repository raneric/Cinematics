package com.sgg.cinematics.di

import com.sgg.cinematics.data.repository.UserInfoRepository
import com.sgg.cinematics.data.repository.impl.UserInfoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UserInfoRepositoryModule {

    @Binds
    abstract fun provideUerRepository(userInfoRepository: UserInfoRepositoryImpl): UserInfoRepository
}
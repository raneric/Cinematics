package com.sgg.cinematics.di

import com.sgg.cinematics.service.AuthService
import com.sgg.cinematics.service.impl.AuthServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class AuthServiceModule {

    @Binds
    abstract fun provideAuthService(authServiceImpl: AuthServiceImpl): AuthService
}
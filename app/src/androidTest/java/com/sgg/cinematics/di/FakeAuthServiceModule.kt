package com.sgg.cinematics.di

import com.sgg.cinematics.service.AuthService
import com.sgg.cinematics.service.FakeAuthService
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn


@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AuthServiceModule::class]
)
abstract class FakeAuthServiceModule {
    @Binds
    abstract fun provideAuthService(authServiceImpl: FakeAuthService): AuthService
}

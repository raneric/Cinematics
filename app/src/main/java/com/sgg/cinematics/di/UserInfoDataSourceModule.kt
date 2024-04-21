package com.sgg.cinematics.di

import com.google.firebase.firestore.FirebaseFirestore
import com.sgg.cinematics.service.UserInfoDataSource
import com.sgg.cinematics.service.impl.RemoteUserInfoDataSource
import com.sgg.cinematics.service.impl.RoomUserInfoDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RemoteDataSource

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RoomDataSource

@InstallIn(SingletonComponent::class)
@Module
class RemoteDataSourceModule {

    @Provides
    @RemoteDataSource
    fun provideUserInfoDataSource(firestore: FirebaseFirestore): UserInfoDataSource {
        return RemoteUserInfoDataSource(firestore)
    }
}

@InstallIn(SingletonComponent::class)
@Module
class RoomDataSourceModule {

    @Provides
    @RoomDataSource
    fun provideUserInfoDataSource(): UserInfoDataSource {
        return RoomUserInfoDataSource()
    }
}
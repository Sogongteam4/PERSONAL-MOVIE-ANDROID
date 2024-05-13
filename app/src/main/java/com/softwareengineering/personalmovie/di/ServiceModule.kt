package com.softwareengineering.personalmovie.di

import com.softwareengineering.personalmovie.data.service.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {
    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit):AuthService=
        retrofit.create(AuthService::class.java)
}
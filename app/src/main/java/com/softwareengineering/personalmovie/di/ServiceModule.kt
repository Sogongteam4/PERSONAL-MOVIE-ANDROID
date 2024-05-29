package com.softwareengineering.personalmovie.di

import android.content.SharedPreferences
import com.softwareengineering.personalmovie.data.service.AuthService
import com.softwareengineering.personalmovie.presentation.HeaderInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {
    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit):AuthService=
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideHeaderIntercepter(autoLoginPrefeneces: SharedPreferences): HeaderInterceptor=
        HeaderInterceptor(autoLoginPrefeneces)

    @Singleton
    @Provides
    fun provideInterceptor(): Interceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }
}
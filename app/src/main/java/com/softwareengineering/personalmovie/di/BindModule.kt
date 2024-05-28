package com.softwareengineering.personalmovie.di

import android.content.SharedPreferences
import com.softwareengineering.personalmovie.data.datasource.AuthDataSource
import com.softwareengineering.personalmovie.data.datasourceImpl.AuthDataSourceImpl
import com.softwareengineering.personalmovie.data.repositoryImpl.AuthRepositoryImpl
import com.softwareengineering.personalmovie.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BindModule {
    @Binds
    @Singleton
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl):AuthRepository

    @Binds
    @Singleton
    abstract fun provideAuthDataSource(authDataSourceImpl: AuthDataSourceImpl):AuthDataSource
}
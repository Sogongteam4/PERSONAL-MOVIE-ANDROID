package com.softwareengineering.personalmovie.data.repositoryImpl

import com.softwareengineering.personalmovie.data.datasource.AuthDataSource
import com.softwareengineering.personalmovie.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
):AuthRepository{
}
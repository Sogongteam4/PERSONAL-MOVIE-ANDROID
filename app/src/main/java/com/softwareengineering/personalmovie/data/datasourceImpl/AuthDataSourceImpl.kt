package com.softwareengineering.personalmovie.data.datasourceImpl

import com.softwareengineering.personalmovie.data.datasource.AuthDataSource
import com.softwareengineering.personalmovie.data.service.AuthService
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authService: AuthService
):AuthDataSource{

}
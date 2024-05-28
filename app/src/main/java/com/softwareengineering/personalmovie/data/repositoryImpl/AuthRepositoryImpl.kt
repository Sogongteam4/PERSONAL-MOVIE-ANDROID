package com.softwareengineering.personalmovie.data.repositoryImpl

import android.util.Log
import com.softwareengineering.personalmovie.data.datasource.AuthDataSource
import com.softwareengineering.personalmovie.data.requestDto.RequestAccessDto
import com.softwareengineering.personalmovie.data.requestDto.RequestTypeDto
import com.softwareengineering.personalmovie.data.responseDto.ResponseAccessDto
import com.softwareengineering.personalmovie.data.responseDto.ResponseMovieDto
import com.softwareengineering.personalmovie.data.responseDto.ResponseSurveyDto
import com.softwareengineering.personalmovie.data.responseDto.ResponseTypeDto
import com.softwareengineering.personalmovie.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
):AuthRepository{
    override suspend fun getAccessToken(token: String): Result<ResponseAccessDto> {
        return runCatching{
            authDataSource.getAccessToken(RequestAccessDto(token))
        }
    }

    override suspend fun getSurvey(token:String, surveyId: Int): Result<ResponseSurveyDto> {
        return runCatching{
            authDataSource.getSurvey(token, surveyId)
        }
    }

    override suspend fun getType(token:String, choices: List<Int>): Result<ResponseTypeDto> {
        return runCatching {
            authDataSource.getType(token, RequestTypeDto(choices))
        }
    }

    override suspend fun getMovie(token: String, movieId: Int): Result<ResponseMovieDto> {
        return runCatching{
            authDataSource.getMovie(token, movieId)
        }
    }
}
package com.softwareengineering.personalmovie.data.datasourceImpl

import com.softwareengineering.personalmovie.data.datasource.AuthDataSource
import com.softwareengineering.personalmovie.data.requestDto.RequestAccessDto
import com.softwareengineering.personalmovie.data.requestDto.RequestTypeDto
import com.softwareengineering.personalmovie.data.responseDto.ResponseAccessDto
import com.softwareengineering.personalmovie.data.responseDto.ResponseMovieDto
import com.softwareengineering.personalmovie.data.responseDto.ResponseSurveyDto
import com.softwareengineering.personalmovie.data.responseDto.ResponseTypeDto
import com.softwareengineering.personalmovie.data.service.AuthService
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authService: AuthService
):AuthDataSource{
    override suspend fun getAccessToken(token: RequestAccessDto): ResponseAccessDto = authService.getAccessToken(token)
    override suspend fun getSurvey(token:String,surveyId:Int): ResponseSurveyDto =authService.getServey(token, surveyId)
    override suspend fun getType(token:String, choices: RequestTypeDto): ResponseTypeDto = authService.getType(token, choices)
    override suspend fun getMovie(token: String, movieId: Int): ResponseMovieDto = authService.getMovie(token,movieId)
}
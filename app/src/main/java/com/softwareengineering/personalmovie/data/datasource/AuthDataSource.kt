package com.softwareengineering.personalmovie.data.datasource

import com.softwareengineering.personalmovie.data.requestDto.RequestAccessDto
import com.softwareengineering.personalmovie.data.requestDto.RequestTypeDto
import com.softwareengineering.personalmovie.data.responseDto.ResponseAccessDto
import com.softwareengineering.personalmovie.data.responseDto.ResponseMovieDto
import com.softwareengineering.personalmovie.data.responseDto.ResponseSurveyDto
import com.softwareengineering.personalmovie.data.responseDto.ResponseTypeDto

interface AuthDataSource {
    suspend fun getAccessToken(
        token:RequestAccessDto
    ):ResponseAccessDto

    suspend fun getSurvey(
        token:String,
        surveyId:Int
    ):ResponseSurveyDto
    suspend fun getType(
        token:String,
        choices:RequestTypeDto
    ):ResponseTypeDto

    suspend fun getMovie(
        token:String,
        movieId:Int
    ): ResponseMovieDto
}
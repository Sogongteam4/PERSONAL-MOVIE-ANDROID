package com.softwareengineering.personalmovie.domain.repository

import com.softwareengineering.personalmovie.data.responseDto.ResponseAccessDto
import com.softwareengineering.personalmovie.data.responseDto.ResponseMovieDto
import com.softwareengineering.personalmovie.data.responseDto.ResponseSurveyDto
import com.softwareengineering.personalmovie.data.responseDto.ResponseTypeDto

interface AuthRepository {
    suspend fun getAccessToken(
        token:String,
    ):Result<ResponseAccessDto>

    suspend fun getSurvey(
        token:String,
        surveyId:Int,
    ):Result<ResponseSurveyDto>

    suspend fun getType(
        token:String,
        choices:List<Int>
    ):Result<ResponseTypeDto>

    suspend fun getMovie(
        token: String,
        movieId:Int
    ):Result<ResponseMovieDto>
}
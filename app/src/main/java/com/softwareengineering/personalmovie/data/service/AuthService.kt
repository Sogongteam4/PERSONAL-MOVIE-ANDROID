package com.softwareengineering.personalmovie.data.service

import com.softwareengineering.personalmovie.data.requestDto.RequestAccessDto
import com.softwareengineering.personalmovie.data.requestDto.RequestTypeDto
import com.softwareengineering.personalmovie.data.responseDto.ResponseAccessDto
import com.softwareengineering.personalmovie.data.responseDto.ResponseMovieDto
import com.softwareengineering.personalmovie.data.responseDto.ResponseSurveyDto
import com.softwareengineering.personalmovie.data.responseDto.ResponseTypeDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthService {
    @POST("api/users")
    suspend fun getAccessToken(
        @Body requestAccessDto: RequestAccessDto
    ): ResponseAccessDto

    @GET("api/surveys/{surveyId}")
    suspend fun getServey(
        @Header("Authorization") token:String,
        @Path("surveyId") surveyId:Int
    ): ResponseSurveyDto

    @POST("api/types")
    suspend fun getType(
        @Header("Authorization") token:String,
        @Body requestTypeDto: RequestTypeDto
    ):ResponseTypeDto

    @GET("api/movies/{movieId}")
    suspend fun getMovie(
        @Header("Authorization") token:String,
        @Path("movieId") movieId:Int
    ):ResponseMovieDto
}
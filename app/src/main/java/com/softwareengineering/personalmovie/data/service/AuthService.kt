package com.softwareengineering.personalmovie.data.service

import retrofit2.http.GET

interface AuthService {
    @GET("/oauth2/authorization/kakao")
    suspend fun getSite()
}
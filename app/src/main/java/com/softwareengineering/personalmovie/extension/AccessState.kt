package com.softwareengineering.personalmovie.extension

sealed class AccessState {
    data object Loading:AccessState()
    data class Success(val accessToken:String):AccessState()
    data class Error(val message:String):AccessState()
}
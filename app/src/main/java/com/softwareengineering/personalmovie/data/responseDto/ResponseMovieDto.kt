package com.softwareengineering.personalmovie.data.responseDto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseMovieDto (
    @SerialName("status")
    val status:Int,
    @SerialName("message")
    val message:String,
    @SerialName("data")
    val data:Data,
){
    @Serializable
    data class Data(
        @SerialName("name")
        val name:String,
        @SerialName("releaseYear")
        val releaseYear:Int,
        @SerialName("trailerUri")
        val trailerUri:String,
        @SerialName("rate")
        val rate:Float,
        @SerialName("genres")
        val genres:List<String>,
    )
}
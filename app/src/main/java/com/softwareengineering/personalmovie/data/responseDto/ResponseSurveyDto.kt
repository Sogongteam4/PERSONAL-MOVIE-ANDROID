package com.softwareengineering.personalmovie.data.responseDto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseSurveyDto (
    @SerialName("status")
    val status:Int,
    @SerialName("message")
    val message:String,
    @SerialName("data")
    val data:Data,
){
    @Serializable
    data class Data(
        @SerialName("question")
        val question:String,
        @SerialName("choices")
        val choices:List<Choices>,
    )
    @Serializable
    data class Choices(
        @SerialName("id")
        val id:Int,
        @SerialName("content")
        val content:String,
    )
}
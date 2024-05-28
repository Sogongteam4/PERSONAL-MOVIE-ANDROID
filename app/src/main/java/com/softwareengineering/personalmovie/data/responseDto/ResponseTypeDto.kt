package com.softwareengineering.personalmovie.data.responseDto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class ResponseTypeDto (
    @SerialName ("status")
    val status:Int,
    @SerialName("message")
    val message:String,
    @SerialName("data")
    val data:Data
){
    @Serializable
    data class Data(
        @SerialName("type")
        val type:String,
        @SerialName("imgUri")
        val imgUri:String,
        @SerialName("movieIds")
        val movieIds:List<Int>
    )
}
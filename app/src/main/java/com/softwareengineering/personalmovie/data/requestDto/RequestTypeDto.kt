package com.softwareengineering.personalmovie.data.requestDto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestTypeDto (
    @SerialName("choices")
    val choices:List<Int>
)
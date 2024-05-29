package com.softwareengineering.personalmovie.data

import java.io.Serializable

data class Movie (
    val year: Int,
    val video: Int, //drawable
    val name: String,
    val tag: List<Genre>,
    val rating: Int
) : Serializable {
    data class Genre(
        val genre: String
    ) : Serializable
}

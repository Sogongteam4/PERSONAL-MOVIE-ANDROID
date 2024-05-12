package com.softwareengineering.personalmovie.data

data class Movie (
    val year:Int,
    val video:Int,//drawable
    val name:String,
    val tag:List<Genre>,
    val grade:Int
){
    data class Genre(
        val genre:String
    )
}
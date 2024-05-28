package com.softwareengineering.personalmovie.presentation.more.result

import com.softwareengineering.personalmovie.R
import com.softwareengineering.personalmovie.data.Movie

object MockDataListProvider {
    fun getMockDataList():List<Movie>{
        return listOf(
            Movie(1995, R.drawable.toystory, "Toy Story", listOf(
                Movie.Genre("Adventure"),
                Movie.Genre("Animation"),
                Movie.Genre("Comedy"),
                Movie.Genre("Children")
            ), 4),
            Movie(1996,R.drawable.jumanji,"Jumanji", listOf(
                Movie.Genre("Adventure"),
                Movie.Genre("Children"),
                Movie.Genre("Fantsy"),
            ), 3),
            Movie(1996, R.drawable.blacksheep, "Black Sheep", listOf(
                Movie.Genre("Comedy")
            ), 3),
            Movie(1995, R.drawable.tomandhuck,"Tom and Huck",listOf(
                Movie.Genre("Adventure"),
                Movie.Genre("Children"),
            ), 3),
            Movie(1995, R.drawable.goldeneye,"GoldenEye",listOf(
                Movie.Genre("Action"),
                Movie.Genre("Adventure"),
                Movie.Genre("Thriller"),
            ), 4),
            Movie(1995, R.drawable.balto,"Balto",listOf(
                Movie.Genre("Adventure"),
                Movie.Genre("Animation"),
                Movie.Genre("Children"),
            ), 3),
            Movie(1995, R.drawable.cutthroatisland,"Cutthroat Island",listOf(
                Movie.Genre("Action"),
                Movie.Genre("Adventure"),
                Movie.Genre("Romance"),
            ), 5),
            Movie(1994, R.drawable.lamerica,"Lamerica",listOf(
                Movie.Genre("Adventure"),
                Movie.Genre("Drama"),
            ), 4),
            Movie(1996, R.drawable.whitesquall,"White Squall",listOf(
                Movie.Genre("Action"),
                Movie.Genre("Adventure"),
                Movie.Genre("Drama"),
            ), 4),
        )
    }
}
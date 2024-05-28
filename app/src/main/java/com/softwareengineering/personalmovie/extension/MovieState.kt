package com.softwareengineering.personalmovie.extension

import com.softwareengineering.personalmovie.data.responseDto.ResponseMovieDto


sealed class MovieState {
    data object Loading:MovieState()
    data class Success(val movie:ResponseMovieDto.Data):MovieState()
    data object Error:MovieState()
}


package com.softwareengineering.personalmovie.extension

import com.softwareengineering.personalmovie.data.responseDto.ResponseTypeDto

sealed class TypeState {
    data object Loading:TypeState()
    data class Success(val data:ResponseTypeDto.Data):TypeState()
    data object Error:TypeState()
}
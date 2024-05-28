package com.softwareengineering.personalmovie.extension

import com.softwareengineering.personalmovie.data.responseDto.ResponseSurveyDto

sealed class SurveyState {
    data object Loading:SurveyState()
    data class Success(val survey:ResponseSurveyDto.Data):SurveyState()
    data object Error:SurveyState()
}
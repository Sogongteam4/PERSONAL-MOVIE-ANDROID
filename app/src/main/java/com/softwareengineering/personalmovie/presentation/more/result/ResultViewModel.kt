package com.softwareengineering.personalmovie.presentation.more.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ResultViewModel:ViewModel() {
    private val _detail:MutableLiveData<Boolean> = MutableLiveData(false)
    var detail: LiveData<Boolean> = _detail

    fun changeDetailState(isDetailShow:Boolean){
        _detail.value=isDetailShow
    }
}
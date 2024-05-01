package com.softwareengineering.personalmovie.presentation.questions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QuestionViewModel:ViewModel() {
    private val _answerList = MutableLiveData<MutableList<Int>>()
    val answerList: LiveData<MutableList<Int>>
        get()=_answerList

    init{
        _answerList.value= mutableListOf()
    }

    fun addToAnswerList(item:Int){
        _answerList.value?.apply {
            add(item)
            _answerList.value = this
        }
    }

    fun deleteBeforeAnswer(){
        _answerList.value?.apply {
            if (isNotEmpty()) {
                removeAt(size - 1)
                _answerList.value = this
            }
        }
    }

    fun clearAnswerList(){
        _answerList.value?.clear()
    }
}
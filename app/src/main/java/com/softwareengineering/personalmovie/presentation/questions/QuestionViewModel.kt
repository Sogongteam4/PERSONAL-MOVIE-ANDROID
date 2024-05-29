package com.softwareengineering.personalmovie.presentation.questions

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QuestionViewModel:ViewModel() {
    private val _token = MutableLiveData<String>()
    val token: LiveData<String> get() = _token

    fun setToken(token: String) {
        _token.value = token
    }

    private val _answerMap = MutableLiveData<MutableMap<Int,Int>>()

    fun addToAnswerMap(questionNum:Int, item:Int){
        val currentMap=_answerMap.value?: mutableMapOf()
        currentMap[questionNum] = item
        _answerMap.value=currentMap
    }

    fun haveAnswer(questionNum: Int){
        val currentMap=_answerMap.value?: mutableMapOf()
        if(currentMap.size==questionNum){
            _answerMap.value=deleteBeforeAnswer(currentMap)
        }
    }

    private fun deleteBeforeAnswer(currentMap:MutableMap<Int,Int>):MutableMap<Int,Int>{
        val lastKey=currentMap.keys.lastOrNull()
        if (lastKey != null) {
            currentMap.remove(lastKey)
        }
        return currentMap
    }

    fun clearAnswerMap(){
        _answerMap.value?.clear()
    }

    fun getAnswerListAsBundle():Bundle{
        val bundle=Bundle()
        var answerList: MutableList<Int> = mutableListOf()
        for(i in _answerMap.value!!.values){
            answerList.add(i)
        }

        bundle.putIntegerArrayList("answerList",ArrayList(answerList))
        return bundle
    }

    /*fun getSurvey(){
        Log.d("questionviewmodel","token:${_token.value}")
        viewModelScope.launch {
            authRepository.getSurvey(_token.value!!, questionNum).onSuccess { response ->
                if(response.status==200){
                    _surveyState.value=SurveyState.Success(response.data)
                    Log.d("questionviewmodel","${response.data.question}")
                }else{
                    Log.d("questionviewmodel","${response.message}")
                    _surveyState.value= SurveyState.Error
                }
            }.onFailure {
                _surveyState.value= SurveyState.Error
                Log.e("questionviewmodel", "Error:${it.message}")
                Log.e("questionviewmodel", Log.getStackTraceString(it))
                if (it is HttpException) {
                    try {
                        val errorBody: ResponseBody? = it.response()?.errorBody()
                        val errorBodyString = errorBody?.string() ?: ""

                        // JSONObject를 사용하여 메시지 추출
                        val jsonObject = JSONObject(errorBodyString)
                        val errorMessage = jsonObject.optString("message", "Unknown error")

                        // 추출된 에러 메시지 로깅
                        Log.e("questionviewmodel", "Error message: $errorMessage")
                    } catch (e: Exception) {
                        // JSON 파싱 실패 시 로깅
                        Log.e("questionviewmodel", "Error parsing error body", e)
                    }
                }
            }
        }
    }*/






}
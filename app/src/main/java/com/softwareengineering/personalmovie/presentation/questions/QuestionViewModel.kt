package com.softwareengineering.personalmovie.presentation.questions

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softwareengineering.personalmovie.data.repositoryImpl.AuthRepositoryImpl
import com.softwareengineering.personalmovie.domain.repository.AuthRepository
import com.softwareengineering.personalmovie.extension.SurveyState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(
    private val authRepository: AuthRepository
) :ViewModel() {
    private val _token = MutableLiveData<String>()
    val token: LiveData<String> get() = _token
    private var currentSurveyId=1
    private val _surveyState= MutableStateFlow<SurveyState>(SurveyState.Loading)
    val surveyState:StateFlow<SurveyState>
        get()=_surveyState
    private val _isLastSurvey=MutableLiveData<Boolean>(false)
    val isLastSurvey:LiveData<Boolean> get() = _isLastSurvey
    private val _answerMap = MutableLiveData<MutableMap<Int,Int>>()
    fun setToken(token: String) {
        _token.value = token
    }



    fun addToAnswerMap(answerId:Int){
        Log.d("questionviewmodel","answerid: ${answerId}")
        val currentMap=_answerMap.value?: mutableMapOf()
        currentMap[currentSurveyId-1] = answerId
        _answerMap.value=currentMap
    }

    fun clearAnswerMap(){
        _answerMap.value?.clear()
    }

    fun getAnswerListAsBundle():Bundle{
        val bundle=Bundle()
        var answerList: MutableList<Int> = mutableListOf()
        for(i in _answerMap.value!!.values){
            answerList.add(i)
            Log.d("questionviewmodel","answer: ${i}")
        }

        bundle.putIntegerArrayList("answerList",ArrayList(answerList))
        return bundle
    }

    fun getSurvey(){
        if(currentSurveyId==5){
            _isLastSurvey.value=true
            return
        }
        viewModelScope.launch {
            authRepository.getSurvey(_token.value!!, currentSurveyId++).onSuccess { response ->
                if(response.status==200){
                    _surveyState.value= SurveyState.Success(response.data)
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
    }

}
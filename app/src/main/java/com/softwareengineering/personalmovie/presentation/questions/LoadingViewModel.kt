package com.softwareengineering.personalmovie.presentation.questions

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softwareengineering.personalmovie.data.repositoryImpl.AuthRepositoryImpl
import com.softwareengineering.personalmovie.domain.repository.AuthRepository
import com.softwareengineering.personalmovie.extension.TypeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class LoadingViewModel @Inject constructor(
    private val authRepository:AuthRepository
):ViewModel() {
    private val _typeState= MutableStateFlow<TypeState>(TypeState.Loading)
    val typeState: StateFlow<TypeState>
        get()=_typeState.asStateFlow()

    fun getType(token:String, answerList:List<Int>){
        viewModelScope.launch {
            authRepository.getType(token, answerList).onSuccess {response ->
                if(response.status==200){
                    _typeState.value= TypeState.Success(response.data)
                    Log.d("loadingviewmodel","${response.data.type}")
                }else{
                    Log.d("loadingviewmodel","${response.message}")
                    _typeState.value= TypeState.Error
                }
            }.onFailure {
                _typeState.value= TypeState.Error
                Log.e("loadingviewmodel", "Error:${it.message}")
                Log.e("loadingviewmodel", Log.getStackTraceString(it))
                if (it is HttpException) {
                    try {
                        val errorBody: ResponseBody? = it.response()?.errorBody()
                        val errorBodyString = errorBody?.string() ?: ""

                        // JSONObject를 사용하여 메시지 추출
                        val jsonObject = JSONObject(errorBodyString)
                        val errorMessage = jsonObject.optString("message", "Unknown error")

                        // 추출된 에러 메시지 로깅
                        Log.e("loadingviewmodel", "Error message: $errorMessage")
                    } catch (e: Exception) {
                        // JSON 파싱 실패 시 로깅
                        Log.e("loadingviewmodel", "Error parsing error body", e)
                    }
                }
            }
        }
    }
}
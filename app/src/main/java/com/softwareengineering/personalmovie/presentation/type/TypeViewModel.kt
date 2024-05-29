package com.softwareengineering.personalmovie.presentation.type

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softwareengineering.personalmovie.data.responseDto.ResponseMovieDto
import com.softwareengineering.personalmovie.domain.repository.AuthRepository
import com.softwareengineering.personalmovie.extension.MovieState
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
class TypeViewModel @Inject constructor(
    private val authRepository: AuthRepository
):ViewModel() {
    private val _movieState=MutableStateFlow<MovieState>(MovieState.Loading)
    val movieState: StateFlow<MovieState>
        get()=_movieState.asStateFlow()
    private val _movieList = MutableLiveData<List<ResponseMovieDto.Data>>()

    // 외부 접근 가능한 LiveData
    val movieList: LiveData<List<ResponseMovieDto.Data>> = _movieList
    private var token:String ?= null


    fun getMovie(movieId:Int) {
        viewModelScope.launch {
            authRepository.getMovie(token!!, movieId).onSuccess { response ->
                _movieState.value=MovieState.Success(response.data)
                Log.d("typeviewmodel","getmovie-${response.data.name}")

                // 기존 리스트를 가져와서 새로운 데이터를 추가한 후 업데이트
                val currentList = _movieList.value.orEmpty().toMutableList()
                currentList.add(response.data)
                _movieList.value = currentList
            }.onFailure {
                _movieState.value=MovieState.Error

                Log.e("typeviewmodel", "Error:${it.message}")
                Log.e("typeviewmodel", Log.getStackTraceString(it))
                if (it is HttpException) {
                    try {
                        val errorBody: ResponseBody? = it.response()?.errorBody()
                        val errorBodyString = errorBody?.string() ?: ""

                        // JSONObject를 사용하여 메시지 추출
                        val jsonObject = JSONObject(errorBodyString)
                        val errorMessage = jsonObject.optString("message", "Unknown error")

                        // 추출된 에러 메시지 로깅
                        Log.e("typeviewmodel", "Error message: $errorMessage")
                    } catch (e: Exception) {
                        // JSON 파싱 실패 시 로깅
                        Log.e("typeviewmodel", "Error parsing error body", e)
                    }
                }
            }
        }
    }

    fun setToken(token:String) {
        this.token=token
    }

    fun getToken():String = token!!
}
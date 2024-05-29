package com.softwareengineering.personalmovie.presentation.questions

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.softwareengineering.personalmovie.R
import com.softwareengineering.personalmovie.databinding.ActivityLoadingBinding
import com.softwareengineering.personalmovie.presentation.type.TypeActivity
import com.bumptech.glide.Glide
import com.softwareengineering.personalmovie.data.responseDto.ResponseTypeDto
import com.softwareengineering.personalmovie.extension.TypeState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.util.ArrayList

@AndroidEntryPoint
class LoadingActivity: AppCompatActivity() {
    private lateinit var binding:ActivityLoadingBinding
    private val loadingViewModel:LoadingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinds()
        showLoadingImage()
    }

    private fun initBinds(){
        binding= ActivityLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun showLoadingImage(){
        Glide.with(this).load(R.raw.clapperboard).into(binding.ivFrameLoading)
        Handler(Looper.getMainLooper()).post {
            getType()
        }
    }

    private fun getType(){
        val answerBundle: Bundle? = intent.getBundleExtra("answerData")
        val answerData:ArrayList<Int>?=answerBundle?.getIntegerArrayList("answerList")
        val token=intent.getStringExtra("accessToken")

        Log.d("loadingactivity","token:$token")
        loadingViewModel.getType(token!!, answerData!!.toList())
        lifecycleScope.launch {
            loadingViewModel.typeState.collect{typeState ->
                when(typeState){
                    is TypeState.Success ->{
                        Log.d("loadingActivity","type success: ${typeState.data.type}")
                        val json=Json.encodeToString(ResponseTypeDto.Data.serializer(), typeState.data)
                        delay(2000)
                        var intent = Intent(this@LoadingActivity, TypeActivity::class.java)
                        intent.putExtra("token",token)
                        intent.putExtra("typeData",json)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    }
                    is TypeState.Loading ->{

                    }
                    is TypeState.Error->{

                    }
                }
            }
        }
    }
}
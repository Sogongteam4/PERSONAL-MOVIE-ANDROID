package com.softwareengineering.personalmovie.presentation

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.softwareengineering.personalmovie.MyApp
import com.softwareengineering.personalmovie.R
import com.softwareengineering.personalmovie.databinding.ActivityStartBinding
import com.softwareengineering.personalmovie.extension.AccessState
import com.softwareengineering.personalmovie.presentation.questions.QuestionActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StartActivity:AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding
    private lateinit var kakaoAuthViewModel:KakaoAuthViewModel
    private val allViewModel:AllViewModel by viewModels()
   // private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinds()
        setting()
        clickButton()
    }

    private fun initBinds(){
        binding= ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setting(){
        val factory = KakaoAuthViewModelFactory(application, allViewModel)
        kakaoAuthViewModel = ViewModelProvider(this, factory).get(KakaoAuthViewModel::class.java)
        //prefs = getSharedPreferences("tokenPrefs", Context.MODE_PRIVATE)
        isLogin()
    }

    private fun clickButton() {
        binding.btnLogin.setOnClickListener {
            kakaoAuthViewModel.kakaoLogin()
        }
    }

    private fun isLogin(){
        lifecycleScope.launch {
            kakaoAuthViewModel.isLoggedIn.collect{
                when(it){
                    true -> {
                        collectAccessState()
                    }
                    false -> Log.d("startactivity","login failed")
                }
            }
        }
    }

    private fun collectAccessState(){
        lifecycleScope.launch {
            allViewModel.accessState.collect{accessState ->
                when(accessState){
                    is AccessState.Success -> {
                        Log.d("startactivity","token: ${accessState.accessToken}")
                        val intent = Intent(this@StartActivity, QuestionActivity::class.java).apply {
                            putExtra("accessToken",accessState.accessToken)
                            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        }
                        startActivity(intent)
                        finish()
                    }
                    is AccessState.Error ->{
                        Log.e("startactivity","token doesn't exist!! ${accessState.message}")
                    }

                    is AccessState.Loading->{

                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
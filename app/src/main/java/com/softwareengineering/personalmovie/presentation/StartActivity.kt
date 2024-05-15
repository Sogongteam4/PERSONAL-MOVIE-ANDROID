package com.softwareengineering.personalmovie.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.softwareengineering.personalmovie.R
import com.softwareengineering.personalmovie.databinding.ActivityStartBinding
import com.softwareengineering.personalmovie.presentation.questions.QuestionActivity
import kotlinx.coroutines.launch

class StartActivity:AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding
    private val kakaoAuthViewModel:KakaoAuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinds()
        clickButton()
    }

    private fun initBinds(){
        binding= ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun clickButton() {
        binding.btnLogin.setOnClickListener {
            kakaoAuthViewModel.kakaoLogin()
        }

        lifecycleScope.launch {
            kakaoAuthViewModel.isLoggedIn.collect{
                when(it){
                    true -> {
                        //binding.tvLogin.text="카카오계정으로 로그인 성공"
                        var intent = Intent(this@StartActivity, QuestionActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    false -> binding.tvLogin.text="카카오계정으로 로그인 실패"
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
package com.softwareengineering.personalmovie.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.softwareengineering.personalmovie.databinding.ActivityStartBinding
import com.softwareengineering.personalmovie.presentation.questions.QuestionActivity

class StartActivity:AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinds()
        clickButton()
    }

    private fun initBinds(){
        binding= ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun clickButton(){
        binding.btnClick.setOnClickListener {
            var intent = Intent(this, QuestionActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
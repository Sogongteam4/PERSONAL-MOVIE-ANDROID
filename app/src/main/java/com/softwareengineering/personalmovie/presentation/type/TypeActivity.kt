package com.softwareengineering.personalmovie.presentation.type

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.softwareengineering.personalmovie.R
import com.softwareengineering.personalmovie.data.responseDto.ResponseTypeDto
import com.softwareengineering.personalmovie.databinding.ActivityTypeBinding
import com.softwareengineering.personalmovie.presentation.questions.QuestionActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class TypeActivity:AppCompatActivity() {
    private lateinit var binding: ActivityTypeBinding
    private val typeViewModel: TypeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinds()
        setFragment()
    }

    private fun initBinds(){
        binding= ActivityTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setFragment(){
        val token:String= intent.getStringExtra("token").toString()
        typeViewModel.setToken(token)
        val jsonData=intent.getStringExtra("typeData")
        if(jsonData!=null){
            val typeStateData= Json.decodeFromString<ResponseTypeDto.Data>(jsonData)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fcv_main, TypeFragment(typeStateData, typeViewModel))
                .commit()
        }

    }

    fun restartQuestionActivity(){
        var intent = Intent(this, QuestionActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("accessToken",typeViewModel.getToken())
        startActivity(intent)
        finish()
    }

    fun replaceActivity(activity: AppCompatActivity){
        var intent = Intent(this, activity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("token",typeViewModel.getToken())
        startActivity(intent)
        finish()
    }
}
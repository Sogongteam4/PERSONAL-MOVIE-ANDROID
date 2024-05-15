package com.softwareengineering.personalmovie.presentation.type

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.softwareengineering.personalmovie.R
import com.softwareengineering.personalmovie.databinding.ActivityTypeBinding
import com.softwareengineering.personalmovie.presentation.LoadingActivity
import com.softwareengineering.personalmovie.presentation.questions.QuestionActivity
import com.softwareengineering.personalmovie.presentation.type.racoon.RacoonFragment

class TypeActivity:AppCompatActivity() {
    private lateinit var binding: ActivityTypeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinds()
        setFragment(RacoonFragment())
    }

    private fun initBinds(){
        binding= ActivityTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fcv_main, fragment)
            .commit()
    }

    fun replaceActivity(activity: AppCompatActivity){
        var intent = Intent(this, activity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}
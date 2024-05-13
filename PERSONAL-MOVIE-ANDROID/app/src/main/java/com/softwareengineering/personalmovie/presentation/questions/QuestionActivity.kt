package com.softwareengineering.personalmovie.presentation.questions

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.softwareengineering.personalmovie.R
import com.softwareengineering.personalmovie.databinding.ActivityQuestionBinding
import com.softwareengineering.personalmovie.presentation.LoadingActivity

class QuestionActivity:AppCompatActivity() {
    private lateinit var binding: ActivityQuestionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinds()
        setFragment(Question1Fragment())
    }

    private fun initBinds(){
        binding= ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setFragment(fragment: Fragment){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fcv_main, fragment)
                .commit()
    }

    fun switchFragment(fragment:Fragment){
        Handler().postDelayed({
            setFragment(fragment)
        },1000)
        Log.d("change","ChangeFragment!!")
    }

    fun endActivity(){
        var intent = Intent(this, LoadingActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}
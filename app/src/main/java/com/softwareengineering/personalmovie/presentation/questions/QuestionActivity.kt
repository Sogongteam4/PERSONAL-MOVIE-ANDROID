package com.softwareengineering.personalmovie.presentation.questions

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.softwareengineering.personalmovie.R
import com.softwareengineering.personalmovie.databinding.ActivityQuestionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuestionBinding
    private lateinit var questionViewModel: QuestionViewModel
    private lateinit var token:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinds()
        setFragment(Question1Fragment())
    }

    private fun initBinds() {
        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setFragment(fragment: Fragment) {
        questionViewModel = ViewModelProvider(this).get(QuestionViewModel::class.java)
        token = intent.getStringExtra("accessToken")!!
        questionViewModel.setToken(token)
        Log.d("QuestionActivity", "Received access token: $token")

        supportFragmentManager.beginTransaction()
            .replace(R.id.fcv_main, fragment)
            .commit()
    }

    fun switchFragment(fragment: Fragment) {
        Handler().postDelayed({
            setFragment(fragment)
        }, 1000)
        Log.d("change", "ChangeFragment!!")
    }

    fun endActivity() {
        val answerData=questionViewModel.getAnswerListAsBundle()
        var intent = Intent(this, LoadingActivity::class.java).apply{
            putExtra("answerData",answerData)
            putExtra("accessToken",questionViewModel.token.value)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
        finish()
    }
}
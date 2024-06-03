package com.softwareengineering.personalmovie.presentation.more.my

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.softwareengineering.personalmovie.R
import com.softwareengineering.personalmovie.data.responseDto.ResponseTypeDto
import com.softwareengineering.personalmovie.databinding.ActivityMyBinding
import com.softwareengineering.personalmovie.extension.TypeState
import com.softwareengineering.personalmovie.presentation.more.my.mytype.MyFragment
import com.softwareengineering.personalmovie.presentation.type.TypeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class MyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyBinding
    private lateinit var myAdapter: MyAdapter
    private val myViewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinds()
    }

    private fun initBinds() {
        binding = ActivityMyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setting()
        getType()
        showBackButton()
    }

    private fun setting() {
        val token: String = intent.getStringExtra("token").toString()
        myViewModel.setToken(token)
        myAdapter = MyAdapter()
        binding.rvMyType.adapter = myAdapter
        binding.btnBack.setOnClickListener {
            finish()
        }

        myAdapter.updateList()
        for (answer in myAdapter.getAnswerList()) {
            Log.d("myactivity", "answer:${answer}")
            myViewModel.getType(token, answer)
        }
        setClickListener()
    }

    private fun getType() {
        lifecycleScope.launch {
            myViewModel.typeState.collect { typeState ->
                when (typeState) {
                    is TypeState.Success -> {
                        Log.d("myactivity", "type success: ${typeState.data.type}")
                        myAdapter.updateTypeList(typeState.data)
                        myViewModel.setStateLoading()
                    }

                    is TypeState.Loading -> {

                    }

                    is TypeState.Error -> {

                    }
                }
            }
        }
    }

    private fun setClickListener() {
        myAdapter.setOnItemClickListener(object : MyViewHolder.OnItemClickListener {
            override fun showMyType(type: ResponseTypeDto.Data) {
                val typeJson = Json.encodeToString(ResponseTypeDto.Data.serializer(), type)
                val myFragment = MyFragment.newInstance(typeJson)
                myViewModel.changeDetailState(true)
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.slide_in_right,
                        R.anim.slide_out_right
                    )
                    .replace(R.id.fcv_my, myFragment)
                    .addToBackStack(null)
                    .commit()
            }
        })
    }

    private fun showBackButton() {
        myViewModel.detail.observe(this) { showdetail ->
            if (showdetail) {
                binding.btnBack.visibility = View.INVISIBLE
            } else
                binding.btnBack.visibility = View.VISIBLE

        }
    }
}
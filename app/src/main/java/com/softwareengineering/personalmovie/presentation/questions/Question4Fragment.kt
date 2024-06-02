package com.softwareengineering.personalmovie.presentation.questions

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.softwareengineering.personalmovie.R
import com.softwareengineering.personalmovie.databinding.FragmentQuestion4Binding
import com.softwareengineering.personalmovie.presentation.AllViewModel

class Question4Fragment:Fragment() {
    private var _binding: FragmentQuestion4Binding?=null
    private val binding: FragmentQuestion4Binding
        get() = requireNotNull(_binding){"바인딩 객체 생성 안됨"}

    private lateinit var questionViewModel: QuestionViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuestion4Binding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewModel()
        clickButtons()
    }

    private fun setViewModel(){
        val activity=requireActivity() as QuestionActivity
        questionViewModel= ViewModelProvider(activity)[QuestionViewModel::class.java]
    }

    private fun clickButtons(){
        with(binding){
            btn1.setOnClickListener {
                clickListener(1, btn1)
            }
            btn2.setOnClickListener {
                clickListener(2, btn2)
            }
            btn3.setOnClickListener {
                clickListener(3, btn3)
            }
            btn4.setOnClickListener {
                clickListener(4, btn4)
            }
        }
    }

    private fun clickListener(item:Int, btn: Button){
        //questionViewModel.haveAnswer(4)
        //questionViewModel.addToAnswerMap(4, item)
        startAnimation(btn)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun startAnimation(btn: Button){
        val activity=requireActivity() as QuestionActivity

        /*questionViewModel.answerMap.observe(viewLifecycleOwner){map ->
            run {
                Log.d("question4fragment", "map")
            }
        }*/

        val clickedDrawable = resources.getDrawable(R.drawable.btn_clicked)
        val unclickedDrawable = resources.getDrawable(R.drawable.btn_unclicked)

        val drawableList = listOf(unclickedDrawable, clickedDrawable, unclickedDrawable, clickedDrawable)

        val handler = Handler()

        // 버튼 이미지를 번갈아가며 변경하는 애니메이션
        var index = 0
        handler.post(object : Runnable {
            override fun run() {
                if (index < drawableList.size) {
                    btn.background = drawableList[index]
                    index++
                    handler.postDelayed(this, 100) // 0.5초마다 변경
                } else {
                // 애니메이션이 종료된 후에 액티비티를 전환
                activity?.endActivity()
            }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}
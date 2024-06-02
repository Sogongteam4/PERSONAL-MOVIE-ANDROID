package com.softwareengineering.personalmovie.presentation.questions

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.softwareengineering.personalmovie.R
import com.softwareengineering.personalmovie.databinding.FragmentQuestionBinding
import com.softwareengineering.personalmovie.extension.SurveyState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class QuestionFragment : Fragment() {
    private var _binding: FragmentQuestionBinding? = null
    private val binding: FragmentQuestionBinding
        get() = requireNotNull(_binding) { "바인딩 객체 생성 안됨" }
    private lateinit var questionViewModel: QuestionViewModel
    private lateinit var questionAdpater: QuestionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setting()
        getData()
        clickButtons()
    }

    private fun setting() {
        questionViewModel = ViewModelProvider(requireActivity()).get(QuestionViewModel::class.java)
        questionAdpater = QuestionAdapter()
        binding.rvBtn.adapter = questionAdpater
    }

    private fun getData() {
        questionViewModel.getSurvey()
        lifecycleScope.launch {
            questionViewModel.surveyState.collect { surveyState ->
                when (surveyState) {
                    is SurveyState.Success -> {
                        Log.d("questionfragment", "survey success")
                        binding.tvQuestion.text = surveyState.survey.question
                        questionAdpater.updateBtnList(surveyState.survey.choices)
                    }

                    is SurveyState.Loading -> {}
                    is SurveyState.Error -> {
                        Log.e("questionfragment", "surveystate error!")
                    }
                }
            }
        }
    }

    private fun clickButtons() {
        questionAdpater.setOnItemClickListener(object : QuestionViewHolder.OnItemClickListener {
            override fun onItemClicked(answerId: Int, btn: Button) {
                Log.d("questionfragment", "fragment answerid: ${answerId}");
                questionViewModel.addToAnswerMap(answerId)
                startAnimation(btn)
                lifecycleScope.launch {
                    delay(1000) // 1초 딜레이
                    checkLastSurvey()
                    getNextSurvey(btn)
                }
            }
        })
        /* with(binding){
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
         }*/
    }

    private fun checkLastSurvey() {
        lifecycleScope.launch {
            questionViewModel.isLastSurvey.observe(viewLifecycleOwner) { isLastSurvey ->
                if (isLastSurvey) {
                    val activity = requireActivity() as QuestionActivity
                    activity?.endActivity()
                }
            }
        }
    }

    private fun getNextSurvey(btn: Button) {
        btn.background = resources.getDrawable(R.drawable.btn_unclicked)
        questionViewModel.getSurvey()
    }

    private fun startAnimation(btn: Button) {
        val clickedDrawable = resources.getDrawable(R.drawable.btn_clicked)
        val unclickedDrawable = resources.getDrawable(R.drawable.btn_unclicked)

        val drawableList =
            listOf(unclickedDrawable, clickedDrawable, unclickedDrawable, clickedDrawable)

        val handler = Handler()

        // 버튼 이미지를 번갈아가며 변경하는 애니메이션
        var index = 0
        handler.post(object : Runnable {
            override fun run() {
                if (index < drawableList.size) {
                    btn.background = drawableList[index]
                    index++
                    handler.postDelayed(this, 100) // 0.5초마다 변경
                }
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
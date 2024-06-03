package com.softwareengineering.personalmovie.presentation.questions

import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.softwareengineering.personalmovie.data.responseDto.ResponseSurveyDto
import com.softwareengineering.personalmovie.databinding.ItemSurveyBinding

class QuestionViewHolder(
    private val binding:ItemSurveyBinding,
    private val listener:OnItemClickListener
) :RecyclerView.ViewHolder(binding.root) {
    interface OnItemClickListener{
        fun onItemClicked(answerId: Int, btn: Button)
    }
    fun bind(choice:ResponseSurveyDto.Choices){
        binding.btn.text=choice.content
        binding.btn.setOnClickListener{
            listener.onItemClicked(choice.id, binding.btn)
        }
    }
}
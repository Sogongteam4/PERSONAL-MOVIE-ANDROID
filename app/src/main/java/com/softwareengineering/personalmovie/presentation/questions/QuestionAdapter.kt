package com.softwareengineering.personalmovie.presentation.questions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softwareengineering.personalmovie.data.responseDto.ResponseSurveyDto
import com.softwareengineering.personalmovie.databinding.ItemSurveyBinding

class QuestionAdapter() : RecyclerView.Adapter<QuestionViewHolder>() {
    private var btnList:MutableList<ResponseSurveyDto.Choices> = mutableListOf()
    private lateinit var listener:QuestionViewHolder.OnItemClickListener
    fun setOnItemClickListener(listener:QuestionViewHolder.OnItemClickListener){
        this.listener=listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val binding = ItemSurveyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuestionViewHolder(binding,listener)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val genreItem = btnList[position]
        holder.bind(genreItem)
    }

    override fun getItemCount() = btnList.size

    fun updateBtnList(newBtnList:List<ResponseSurveyDto.Choices>){
        btnList.clear()
        btnList.addAll(newBtnList)
        notifyDataSetChanged()
    }
}
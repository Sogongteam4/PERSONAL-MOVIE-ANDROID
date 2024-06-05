package com.softwareengineering.personalmovie.presentation.more.my

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.softwareengineering.personalmovie.data.responseDto.ResponseTypeDto
import com.softwareengineering.personalmovie.databinding.ItemMyBinding
import com.softwareengineering.personalmovie.presentation.questions.QuestionViewHolder

class MyViewHolder(
    private val binding:ItemMyBinding,
    private val listener:OnItemClickListener
):RecyclerView.ViewHolder(binding.root) {
    interface OnItemClickListener{
        fun showMyType(type:ResponseTypeDto.Data)
    }

    fun bind(type:ResponseTypeDto.Data){
        with(binding){
            ivType.load(type.imgUri)
            tvType.text=type.type
        }
        itemView.setOnClickListener{
            listener.showMyType(type)
        }

    }
}
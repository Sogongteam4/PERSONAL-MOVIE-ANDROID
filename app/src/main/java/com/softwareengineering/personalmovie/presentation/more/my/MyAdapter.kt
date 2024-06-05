package com.softwareengineering.personalmovie.presentation.more.my

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softwareengineering.personalmovie.data.responseDto.ResponseTypeDto
import com.softwareengineering.personalmovie.databinding.ItemMyBinding

class MyAdapter :RecyclerView.Adapter<MyViewHolder>(){
    private val typeList:MutableList<ResponseTypeDto.Data> = mutableListOf()
    private lateinit var listener: MyViewHolder.OnItemClickListener
    private val answerList:MutableList<MutableList<Int>> = mutableListOf(
        mutableListOf(),
        mutableListOf(),
        mutableListOf(),
        mutableListOf()
    )
    fun setOnItemClickListener(listener: MyViewHolder.OnItemClickListener){
        this.listener=listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemMyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding,listener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val type = typeList[position]
        holder.bind(type)
    }

    override fun getItemCount() = typeList.size

    fun updateTypeList(type:ResponseTypeDto.Data){
        typeList.add(type)
        notifyDataSetChanged()
    }
    fun updateList() {
        val firstList = listOf(1, 1, 1, 1)
        val secondList = listOf(2, 8, 6, 2)
        val thirdList = listOf(3, 8, 6, 2)
        val lastList = listOf(2, 11, 11, 12)
        answerList.add(firstList.toMutableList())
        answerList.add(secondList.toMutableList())
        answerList.add(thirdList.toMutableList())
        answerList.add(lastList.toMutableList())
    }

    fun getAnswerList(): MutableList<MutableList<Int>> {
        return answerList
    }
}
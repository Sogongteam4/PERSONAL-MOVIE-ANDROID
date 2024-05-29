package com.softwareengineering.personalmovie.presentation.more

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softwareengineering.personalmovie.data.Movie
import com.softwareengineering.personalmovie.databinding.ItemYearBinding

class YearAdapter
    :RecyclerView.Adapter<YearViewHolder>() {
    private var yearList: MutableList<String> = mutableListOf()

    private lateinit var listener: YearViewHolder.OnItemClickListener
    fun setOnItemClickListener(listener: YearViewHolder.OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YearViewHolder {
        val binding = ItemYearBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return YearViewHolder(binding,listener)
    }

    override fun onBindViewHolder(holder: YearViewHolder, position: Int) {
        val yearItem = yearList[position]
        holder.bind(yearItem)
    }

    override fun getItemCount() = yearList.size

//    fun updateYearList(newYearList: List<String>) {
//        yearList.clear()
//        yearList.addAll(newYearList)
//        notifyDataSetChanged() // 모든 아이템을 업데이트
//    }

    fun makeYearList(){
        val list= listOf(
           "1900년대",
            "1910년대",
            "1910년대",
            "1910년대",
            "1910년대",
            "1910년대",
            "1910년대",
            "1910년대",
            "1910년대",
            "1910년대",
            "1910년대",
            "1910년대",
        )
        yearList.addAll(list)
        notifyDataSetChanged()
    }
}
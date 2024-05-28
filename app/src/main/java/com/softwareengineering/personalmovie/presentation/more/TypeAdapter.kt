package com.softwareengineering.personalmovie.presentation.more

import android.content.ClipData.Item
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softwareengineering.personalmovie.databinding.ItemTypeBinding
import com.softwareengineering.personalmovie.databinding.ItemYearBinding

class TypeAdapter : RecyclerView.Adapter<TypeViewHolder>() {
    private var typeList: MutableList<String> = mutableListOf()

    private lateinit var listener: TypeViewHolder.OnItemClickListener
    fun setOnItemClickListener(listener: TypeViewHolder.OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeViewHolder {
        val binding = ItemTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TypeViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: TypeViewHolder, position: Int) {
        val type = typeList[position]
        holder.bind(type)
    }

    override fun getItemCount() = typeList.size

//    fun updateYearList(newYearList: List<String>) {
//        yearList.clear()
//        yearList.addAll(newYearList)
//        notifyDataSetChanged() // 모든 아이템을 업데이트
//    }

    fun makeTypeList() {
        val list = listOf(
            "너구리",
            "사슴",
            "올빼미",
            "치타",
            "나비",
            "원숭이",
            "용",
            "카피바라",
        )
        typeList.addAll(list)
        notifyDataSetChanged()
    }
}
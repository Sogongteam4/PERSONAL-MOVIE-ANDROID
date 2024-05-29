package com.softwareengineering.personalmovie.presentation.more

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.softwareengineering.personalmovie.databinding.ItemYearBinding

class YearViewHolder (
    private val binding:ItemYearBinding,
    private val listener: OnItemClickListener
):RecyclerView.ViewHolder(binding.root){
    interface OnItemClickListener{
        fun replaceActivity(year:String)
    }

    fun bind(year:String){
        with(binding.btnYear){
            text=year
            setOnClickListener{
                listener.replaceActivity(year)
                Log.d("yearviewholder","clicked: ${year}")
            }

        }

    }

}
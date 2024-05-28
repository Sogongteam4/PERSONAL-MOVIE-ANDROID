package com.softwareengineering.personalmovie.presentation.more

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.softwareengineering.personalmovie.databinding.ItemTypeBinding

class TypeViewHolder(
    private val binding:ItemTypeBinding,
    private val listener:OnItemClickListener
) :RecyclerView.ViewHolder(binding.root){
    interface OnItemClickListener{
        fun replaceActivity(type:String)
    }

    fun bind(type:String){
        with(binding.btnType){
            text=type
            setOnClickListener{
                listener.replaceActivity(type)
                Log.d("typeviewholder","clicked: ${type}")
            }
        }
    }
}
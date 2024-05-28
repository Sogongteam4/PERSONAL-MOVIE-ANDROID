package com.softwareengineering.personalmovie.presentation.more.result

import androidx.recyclerview.widget.RecyclerView
import com.softwareengineering.personalmovie.data.Movie
import com.softwareengineering.personalmovie.databinding.ItemResultBinding

class SearchResultViewHolder(
    private val binding:ItemResultBinding,
    private val listener:OnItemClickListener
):RecyclerView.ViewHolder(binding.root) {
    interface OnItemClickListener {
        fun replaceFragment(movie:Movie)
    }

    fun bind(movie:Movie){
        with(binding){
            ivMovie.setImageResource(movie.video)
            tvName.text=movie.name
        }
        itemView.setOnClickListener{
            listener.replaceFragment(movie)
        }
    }

}
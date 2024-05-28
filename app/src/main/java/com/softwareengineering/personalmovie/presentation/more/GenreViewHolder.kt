package com.softwareengineering.personalmovie.presentation.more

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.softwareengineering.personalmovie.data.Movie
import com.softwareengineering.personalmovie.databinding.ItemGenreBinding

class GenreViewHolder(
    private val binding: ItemGenreBinding,
    private val listener: OnItemClickListener
):RecyclerView.ViewHolder(binding.root) {
    interface OnItemClickListener {
        fun replaceActivity(genre:Movie.Genre)
    }
    fun bind(genre: Movie.Genre, position: Int) {
        binding.btnGenre.text = genre.genre // 버튼 텍스트 설정

        // 아이템 클릭 리스너 설정
        binding.btnGenre.setOnClickListener {
            listener.replaceActivity(genre)
            Log.d("genreviewholder","clicked: ${genre.genre}")
        }
    }
}
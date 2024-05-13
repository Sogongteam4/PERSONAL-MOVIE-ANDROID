package com.softwareengineering.personalmovie.presentation.type.owl

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.softwareengineering.personalmovie.R
import com.softwareengineering.personalmovie.data.Movie
import com.softwareengineering.personalmovie.databinding.ItemTypeBinding

class OwlAdapter(private val context:Context, private val movieList:List<Movie>):
    RecyclerView.Adapter<OwlAdapter.OwlPagerViewHolder>() {
    inner class OwlPagerViewHolder(private val binding: ItemTypeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie:Movie) {
            with(binding){
                tvYear.text=movie.year.toString()
                ivVideo.load(movie.video)
                tvName.text=movie.name
                tvGenre.text=movie.tag.toString()
                tvScore.text=context.getString(R.string.score, movie.grade)
                // tv_score의 텍스트 값을 가져와서 Float 값으로 변환하여 레이팅으로 설정
                val scoreText = movie.grade.toFloat()
                rbRating.rating = scoreText
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OwlPagerViewHolder {
        val binding =
            ItemTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OwlPagerViewHolder(binding)
    }
    override fun onBindViewHolder(holder: OwlPagerViewHolder, position: Int) {
        val movie = movieList[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int =movieList.size
}
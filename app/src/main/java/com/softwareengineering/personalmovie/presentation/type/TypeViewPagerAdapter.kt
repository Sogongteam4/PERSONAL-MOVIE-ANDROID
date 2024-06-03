package com.softwareengineering.personalmovie.presentation.type

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.softwareengineering.personalmovie.R
import com.softwareengineering.personalmovie.data.responseDto.ResponseMovieDto
import com.softwareengineering.personalmovie.databinding.ItemDetailBinding

class TypeViewPagerAdapter(private val context:Context, private val movieList:List<ResponseMovieDto.Data>):
    RecyclerView.Adapter<TypeViewPagerAdapter.TypePagerViewHolder>() {
    inner class TypePagerViewHolder(private val binding: ItemDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie:ResponseMovieDto.Data) {
            val scoreText = movie.rate
            with(binding){
                tvYear.text=movie.releaseYear.toString()
                tvName.text=movie.name
                tvGenre.text=movie.genres.joinToString(separator = "") { "@${it} " }
                tvScore.text=context.getString(R.string.score, movie.rate)
                // tv_score의 텍스트 값을 가져와서 Float 값으로 변환하여 레이팅으로 설정
                rbRating.rating = scoreText
            }
            val youTubePlayerView:YouTubePlayerView=binding.pvVideo
            val lifecycleOwner=itemView.findViewTreeLifecycleOwner()
            lifecycleOwner?.lifecycle?.addObserver(youTubePlayerView)

            youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer){
                    val videoId = movie.trailerUri.substringAfter("?v=")
                    youTubePlayer.cueVideo(videoId,0f)
                }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypePagerViewHolder {
        val binding =
            ItemDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TypePagerViewHolder(binding)
    }
    override fun onBindViewHolder(holder: TypePagerViewHolder, position: Int) {
        val movie = movieList[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int =movieList.size
}
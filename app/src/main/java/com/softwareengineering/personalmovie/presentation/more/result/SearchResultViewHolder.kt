package com.softwareengineering.personalmovie.presentation.more.result

import android.util.Log
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.softwareengineering.personalmovie.data.Movie
import com.softwareengineering.personalmovie.data.responseDto.ResponseMovieDto
import com.softwareengineering.personalmovie.databinding.ItemResultBinding

class SearchResultViewHolder(
    private val binding:ItemResultBinding,
    private val listener:OnItemClickListener
):RecyclerView.ViewHolder(binding.root) {
    interface OnItemClickListener {
        fun replaceFragment(movie: ResponseMovieDto.Data)
    }

    fun bind(movie:ResponseMovieDto.Data){
        with(binding){
            tvName.text=movie.name
            Log.d("searchresultviewholder","tvname: $tvName")
        }

        val youTubePlayerView: YouTubePlayerView =binding.pvVideo
        val lifecycleOwner=itemView.findViewTreeLifecycleOwner()
        lifecycleOwner?.lifecycle?.addObserver(youTubePlayerView)

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer){
                val videoId = movie.trailerUri.substringAfter("?v=")
                youTubePlayer.cueVideo(videoId,0f)
            }
        })

        itemView.setOnClickListener{
            listener.replaceFragment(movie)
        }
    }

}
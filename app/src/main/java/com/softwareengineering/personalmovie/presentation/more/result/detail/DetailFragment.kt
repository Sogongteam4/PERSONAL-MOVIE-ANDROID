package com.softwareengineering.personalmovie.presentation.more.result.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.softwareengineering.personalmovie.R
import com.softwareengineering.personalmovie.data.responseDto.ResponseMovieDto
import com.softwareengineering.personalmovie.databinding.FragmentDetailBinding
import com.softwareengineering.personalmovie.databinding.ItemDetailBinding
import com.softwareengineering.personalmovie.presentation.more.result.ResultViewModel

class DetailFragment:Fragment() {
    private var _binding: FragmentDetailBinding?=null
    private val binding: FragmentDetailBinding
        get() = requireNotNull(_binding){"바인딩 객체 생성 안됨"}
    private lateinit var resultViewModel:ResultViewModel
    // FragmentListener.kt
    /*interface FragmentListener {
        fun onMovieSelected(movie: Movie)
    }*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showDetail()
        clickedButton()
    }

    private fun showDetail(){
        resultViewModel= ResultViewModel()
        val movie=arguments?.getSerializable("result")as ResponseMovieDto.Data
        val itemDetailBinding=ItemDetailBinding.bind(binding.itemMovie.root)
        binding.tvTitle.text=movie.name

        val player= context?.let { ExoPlayer.Builder(it).build() }
        player!!.setMediaItem(MediaItem.fromUri(movie.trailerUri))

        with(itemDetailBinding){
            tvYear.text=movie.releaseYear.toString()
            tvName.text=movie.name
            tvGenre.text=movie.genres.joinToString(separator = "") { "@${it} " }
            tvScore.text=requireContext().getString(R.string.score, movie.rate)
            rbRating.rating=movie.rate.toFloat()
        }

        val youTubePlayerView: YouTubePlayerView = itemDetailBinding.pvVideo
        viewLifecycleOwner.lifecycle.addObserver(youTubePlayerView)

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer){
                val videoId = movie.trailerUri.substringAfter("?v=")
                youTubePlayer.loadVideo(videoId, 0f)
            }
        })
    }

    private fun clickedButton(){
        binding.btnBack.setOnClickListener{
            resultViewModel.changeDetailState(false)
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in_right,
                    R.anim.slide_out_right
                )
                .remove(this@DetailFragment).commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}
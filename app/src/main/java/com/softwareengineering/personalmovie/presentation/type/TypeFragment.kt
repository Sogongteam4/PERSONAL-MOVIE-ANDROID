package com.softwareengineering.personalmovie.presentation.type

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.softwareengineering.personalmovie.R
import com.softwareengineering.personalmovie.data.Movie
import com.softwareengineering.personalmovie.data.responseDto.ResponseMovieDto
import com.softwareengineering.personalmovie.data.responseDto.ResponseTypeDto
import com.softwareengineering.personalmovie.databinding.FragmentTypeBinding
import com.softwareengineering.personalmovie.presentation.more.MoreActivity
import kotlin.math.abs

class TypeFragment(
    private val typeData:ResponseTypeDto.Data,
    private val typeViewModel: TypeViewModel
): Fragment() {
    private var _binding: FragmentTypeBinding?=null
    private val binding: FragmentTypeBinding
        get() = requireNotNull(_binding){"바인딩 객체 생성 안됨"}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTypeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    private fun bindAdapter(movieList: List<ResponseMovieDto.Data>) {
        with(binding) {
            vpMovie.adapter = context?.let { TypeViewPagerAdapter(lifecycle, it, movieList) }
            vpMovie.offscreenPageLimit = 4
            vpMovie.orientation = ViewPager2.ORIENTATION_HORIZONTAL

            val offsetBetweenPages = resources.getDimensionPixelOffset(R.dimen.offsetBetweenPages).toFloat()
            vpMovie.setPageTransformer { page, position ->
                val myOffset = position * -(2 * offsetBetweenPages)
                if (position < -1) {
                    page.translationX = -myOffset
                } else if (position <= 1) {
                    val scaleFactor = 0.8f.coerceAtLeast(1 - abs(position))
                    page.translationX = myOffset
                    page.scaleY = scaleFactor
                    page.alpha = scaleFactor
                } else {
                    page.alpha = 0f
                    page.translationX = myOffset
                }
            }

            vpMovie.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    Log.d("ViewPagerFragment", "Page ${position + 1}")
                }
            })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setting()
        setTexts()
        //bindAdapter()
        clickButton()
    }
    private fun observeViewModel() {
        typeViewModel.movieList.observe(viewLifecycleOwner) { movieList ->
            Log.d("RacoonFragment", "Movie list updated: $movieList")
            bindAdapter(movieList)
        }
    }
    private fun setting(){
        for(movieId in typeData.movieIds){
            typeViewModel.getMovie(movieId)
        }
    }

    private fun setTexts(){
        with(binding){
            tvTitle.text=typeData.type
            ivRacoon.load(typeData.imgUri)
        }
    }


    private fun makeMockList():List<Movie>{
        val movieList= listOf(
            Movie(1995, R.drawable.toystory, "Toy Story", listOf(
                Movie.Genre("Adventure"),
                Movie.Genre("Animation"),
                Movie.Genre("Comedy"),
                Movie.Genre("Children")
            ), 4),
            Movie(1996,R.drawable.jumanji,"Jumanji", listOf(
                Movie.Genre("Adventure"),
                Movie.Genre("Children"),
                Movie.Genre("Fantsy"),
            ), 3),
            Movie(1996, R.drawable.blacksheep, "Black Sheep", listOf(
                Movie.Genre("Comedy")
            ), 3)
        )
        return movieList
    }

    private fun clickButton(){
        val activity=requireActivity() as TypeActivity
        binding.btnRestart.setOnClickListener {
            activity.restartQuestionActivity()
        }

        binding.btnMore.setOnClickListener{
            activity.replaceActivity(MoreActivity())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}
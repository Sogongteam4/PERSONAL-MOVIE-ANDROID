package com.softwareengineering.personalmovie.presentation.type

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setting()
        setTexts()
        clickButton()
    }
    private fun observeViewModel() {
        typeViewModel.movieList.observe(viewLifecycleOwner) { movieList ->
            Log.d("RacoonFragment", "Movie list updated: $movieList")
            bindAdapter(movieList)
        }
    }

    private fun bindAdapter(movieList: List<ResponseMovieDto.Data>) {
        with(binding) {
            vpMovie.adapter = context?.let { TypeViewPagerAdapter(it, movieList) }
            vpMovie.offscreenPageLimit = 4
            vpMovie.orientation = ViewPager2.ORIENTATION_HORIZONTAL

            // RecyclerView 설정
            val recyclerView = vpMovie.getChildAt(0) as RecyclerView
            recyclerView.clipToPadding = false
            recyclerView.clipChildren = false
            recyclerView.overScrollMode = RecyclerView.OVER_SCROLL_NEVER

            // ViewPager2에 패딩 추가
            val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.pageMargin)
            val offsetPx = resources.getDimensionPixelOffset(R.dimen.offsetBetweenPages)
            vpMovie.setPadding(offsetPx, 0, offsetPx, 0)
            vpMovie.setClipToPadding(false)
            vpMovie.setClipChildren(false)

            val compositePageTransformer = CompositePageTransformer()
            compositePageTransformer.addTransformer(MarginPageTransformer(pageMarginPx))
            compositePageTransformer.addTransformer { page, position ->
                val scaleFactor = 0.85f.coerceAtLeast(1 - abs(position))
                page.scaleY = scaleFactor
                page.alpha = scaleFactor
                val myOffset = position * -(2 * offsetPx)
                page.translationX = myOffset

                // Z 순서 설정
                page.translationZ = if (position == 0f) 1f else 0f
            }

            vpMovie.setPageTransformer(compositePageTransformer)

            vpMovie.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    Log.d("ViewPagerFragment", "Page ${position + 1}")
                }
            })
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
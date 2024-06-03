package com.softwareengineering.personalmovie.presentation.more.my.mytype

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.softwareengineering.personalmovie.R
import com.softwareengineering.personalmovie.data.responseDto.ResponseMovieDto
import com.softwareengineering.personalmovie.data.responseDto.ResponseTypeDto
import com.softwareengineering.personalmovie.databinding.FragmentTypeBinding
import com.softwareengineering.personalmovie.presentation.more.my.MyActivity
import com.softwareengineering.personalmovie.presentation.more.my.MyViewModel
import com.softwareengineering.personalmovie.presentation.type.TypeViewModel
import com.softwareengineering.personalmovie.presentation.type.TypeViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.json.Json
import kotlin.math.abs

@AndroidEntryPoint
class MyFragment : Fragment(){
    private var _binding: FragmentTypeBinding? = null
    private val binding: FragmentTypeBinding
        get() = requireNotNull(_binding) { "바인딩 객체 생성 안됨" }
    private lateinit var myViewModel: MyViewModel
    private lateinit var type:ResponseTypeDto.Data
    private val typeViewModel:TypeViewModel by viewModels()

    companion object {
        private const val ARG_TYPE_JSON = "arg_type_json"

        fun newInstance(typeJson: String): MyFragment {
            return MyFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TYPE_JSON, typeJson)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val typeJson = arguments?.getString(ARG_TYPE_JSON)
        type = typeJson?.let { Json.decodeFromString(ResponseTypeDto.Data.serializer(), it) }!!
        _binding = FragmentTypeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setting()
        observeViewModel()
    }

    private fun setting() {
        val activity = requireActivity() as MyActivity
        myViewModel = ViewModelProvider(activity)[MyViewModel::class.java]
        typeViewModel.setToken(myViewModel.getToken())
        for(movieId in type.movieIds){
            typeViewModel.getMovie(movieId)
        }
        setTexts()
    }

    private fun setTexts() {
        with(binding) {
            tvTitle.text=type.type
            ivRacoon.load(type.imgUri)
            btnMore.visibility=View.INVISIBLE
            btnRestart.visibility=View.INVISIBLE
            btnBack.setOnClickListener{
                myViewModel.changeDetailState(false)
                parentFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.slide_in_right,
                        R.anim.slide_out_right
                    )
                    .remove(this@MyFragment).commit()
            }
            // maxwidth 설정
            val dpValue = 200  // Example: 200dp
            val density = tvTitle.resources.displayMetrics.density
            val maxWidthPx = (dpValue * density).toInt()  // Convert dp to pixels
            tvTitle.maxWidth = maxWidthPx  // Set the max width
        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
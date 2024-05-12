package com.softwareengineering.personalmovie.presentation.type.dragon

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.softwareengineering.personalmovie.R
import com.softwareengineering.personalmovie.data.Movie
import com.softwareengineering.personalmovie.databinding.FragmentDragonBinding
import com.softwareengineering.personalmovie.presentation.type.TypeActivity
import kotlin.math.abs

class DragonFragment: Fragment() {
    private var _binding: FragmentDragonBinding?=null
    private val binding: FragmentDragonBinding
        get() = requireNotNull(_binding){"바인딩 객체 생성 안됨"}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDragonBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindAdapter()
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickButton()
    }

    private fun bindAdapter(){
        with(binding){
            vpMovie.adapter= context?.let { DragonViewPagerAdapter(it, makeMockList()) }
            // 관리하는 페이지 수. default = 1
            vpMovie.offscreenPageLimit = 4
            vpMovie.orientation=ViewPager2.ORIENTATION_HORIZONTAL

            // item_view 간의 양 옆 여백을 상쇄할 값
            val offsetBetweenPages = resources.getDimensionPixelOffset(R.dimen.offsetBetweenPages).toFloat()
            vpMovie.setPageTransformer { page, position ->
                val myOffset = position * -(2 * offsetBetweenPages)
                if (position < -1) {
                    page.translationX = -myOffset
                } else if (position <= 1) {
                    // Paging 시 Y축 Animation 배경색을 약간 연하게 처리
                    val scaleFactor = 0.8f.coerceAtLeast(1 - abs(position))
                    page.translationX = myOffset
                    page.scaleY = scaleFactor
                    page.alpha = scaleFactor
                } else {
                    page.alpha = 0f
                    page.translationX = myOffset
                }
            }

            vpMovie.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){

                // Paging 완료되면 호출
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    Log.d("ViewPagerFragment", "Page ${position+1}")
                }
            })
        }

    }


    private fun makeMockList():List<Movie>{
        val movieList= listOf(
            Movie(1995, R.drawable.thecityoflostchildren, "The City of Lost Children", listOf(
                Movie.Genre("Adventure"),
                Movie.Genre("Drama"),
                Movie.Genre("Fantasy"),
                Movie.Genre("Mystery"),
                Movie.Genre("Sci-Fi")
            ), 4),
            Movie(1995,R.drawable.waterworld,"Waterworld", listOf(
                Movie.Genre("Action"),
                Movie.Genre("Adventure"),
                Movie.Genre("Sci-Fi")
            ), 3),
            Movie(1995, R.drawable.akidinkingarthurscourt, "A Kid in King Arthur's Court", listOf(
                Movie.Genre("Adventure"),
                Movie.Genre("Children"),
                Movie.Genre("Comedy"),
                Movie.Genre("Fantasy"),
                Movie.Genre("Romance")
            ), 3)
        )
        return movieList
    }

    private fun clickButton(){
        val activity=requireActivity() as TypeActivity
        binding.btnRestart.setOnClickListener {
            activity.restartQuestionActivity()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}
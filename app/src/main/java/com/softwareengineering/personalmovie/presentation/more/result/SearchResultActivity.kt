package com.softwareengineering.personalmovie.presentation.more.result

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.softwareengineering.personalmovie.R
import com.softwareengineering.personalmovie.data.responseDto.ResponseMovieDto
import com.softwareengineering.personalmovie.databinding.ActivitySearchResultBinding
import com.softwareengineering.personalmovie.presentation.more.result.detail.DetailFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class SearchResultActivity:AppCompatActivity() {
    private lateinit var binding:ActivitySearchResultBinding
    private val resultViewModel: ResultViewModel by viewModels()
    private lateinit var searchResultAdapter: SearchResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinds()
    }

    private fun initBinds(){
        binding= ActivitySearchResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setting()
        getText()
    }

    private fun setting() {
        val token:String= intent.getStringExtra("token").toString()
        Log.d("searchresultactivity","token: ${token}")
        val mockList = listOf(1,2,3,4,5,6,7,8,9,10,11,12)

        resultViewModel.setToken(token)
        for(i in mockList){
            resultViewModel.getMovie()
        }

        resultViewModel.movieList.observe(this) { movieList ->
            Log.d("RacoonFragment", "Movie list updated: $movieList")
            searchResultAdapter = SearchResultAdapter(movieList)
            binding.rvResult.adapter = searchResultAdapter
            clickedButton()
        }
    }

    private fun getText(){
        val searchKeyword = intent.getStringExtra("result")
        Log.d("genreFragment", "Item name: ${searchKeyword}")
        binding.tvTitle.text=searchKeyword
    }

    private fun clickedButton(){
        binding.btnBack.setOnClickListener{
            // activity 종료
            finish()
        }

        searchResultAdapter.setOnItemClickListener(object : SearchResultViewHolder.OnItemClickListener {

            override fun replaceFragment(movie: ResponseMovieDto.Data) {
                Log.d("searchresultactivity", " clicked item: ${movie.name}")
                showFragment(movie)
            }
        })
    }
    fun showFragment(movie:ResponseMovieDto.Data){
        val jsonMovie= Json.encodeToString(movie)
        val detailFragment = DetailFragment().apply {
            arguments = Bundle().apply {
                putString("result", jsonMovie)
            }
        }

        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_right
            )
            .add(R.id.fcv_detail, detailFragment) // 현재 화면에 SearchResultFragment가 있는 상태에서 DetailFragment를 추가합니다.
            .addToBackStack(null) // 이 트랜잭션을 백 스택에 추가합니다.
            .commit()
    }
}
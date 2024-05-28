package com.softwareengineering.personalmovie.presentation.more.result

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.softwareengineering.personalmovie.R
import com.softwareengineering.personalmovie.data.Movie
import com.softwareengineering.personalmovie.databinding.ActivitySearchResultBinding
import com.softwareengineering.personalmovie.presentation.more.result.detail.DetailFragment
import java.io.Serializable

class SearchResultActivity:AppCompatActivity() {
    private lateinit var binding:ActivitySearchResultBinding
    private lateinit var resultViewModel: ResultViewModel
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
        clickedButton()
    }

    private fun setting() {
        resultViewModel = ResultViewModel()
        searchResultAdapter = SearchResultAdapter()
        binding.rvResult.adapter = searchResultAdapter
        searchResultAdapter.makeResultList()
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

            override fun replaceFragment(movie: Movie) {
                // 아이템을 BagFragment로 전달
                //Toast.makeText(activity, "${foodItem.lastName}", Toast.LENGTH_SHORT).show()
                Log.d("searchresultactivity", " clicked item: ${movie.name}")
                showFragment(movie)
            }
        })
    }
    fun showFragment(movie:Movie){
        val detailFragment = DetailFragment().apply {
            arguments = Bundle().apply {
                putSerializable("result", movie)
            }
        }
        //resultViewModel.changeDetailState(true)
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_right
            )
            .add(R.id.fcv_detail, detailFragment) // 현재 화면에 SearchResultFragment가 있는 상태에서 DetailFragment를 추가합니다.
            .addToBackStack(null) // 이 트랜잭션을 백 스택에 추가합니다.
            .commit()
    }

    /*fun showDetailFragment(fragment: Fragment) {
        resultViewModel.changeDetailState(true)
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_right
            )
            .add(R.id.fcv_detail, fragment) // 현재 화면에 SearchResultFragment가 있는 상태에서 DetailFragment를 추가합니다.
            .addToBackStack(null) // 이 트랜잭션을 백 스택에 추가합니다.
            .commit()
    }*/
}
package com.softwareengineering.personalmovie.presentation.more

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.softwareengineering.personalmovie.R
import com.softwareengineering.personalmovie.data.Movie
import com.softwareengineering.personalmovie.databinding.ActivityMoreBinding
import com.softwareengineering.personalmovie.presentation.more.result.SearchResultActivity

class MoreActivity:AppCompatActivity() {
    private lateinit var binding: ActivityMoreBinding
    private lateinit var genreAdapter: GenreAdapter
    private lateinit var yearAdapter: YearAdapter
    private lateinit var typeAdapter: TypeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinds()
    }

    private fun initBinds(){
        binding= ActivityMoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        makeButtons()
    }

    private fun makeButtons(){
        genreAdapter= GenreAdapter()
        yearAdapter=YearAdapter()
        typeAdapter=TypeAdapter()
        with(binding){
            rvGenre.adapter = genreAdapter
            rvYears.adapter=yearAdapter
            rvType.adapter=typeAdapter
        }
        genreAdapter.makeMenuList()
        yearAdapter.makeYearList()
        typeAdapter.makeTypeList()
        setClickListener()
    }

    private fun setClickListener() {
        // 아이템 클릭 리스너 설정
        genreAdapter.setOnItemClickListener(object : GenreViewHolder.OnItemClickListener {
            override fun replaceActivity(genre: Movie.Genre) {
                // 아이템을 BagFragment로 전달
                //Toast.makeText(activity, "${foodItem.lastName}", Toast.LENGTH_SHORT).show()
                //장르 이름 결과 fragment에 넘겨줌
                deliverData(genre.genre)
            }
        })

        yearAdapter.setOnItemClickListener(object :YearViewHolder.OnItemClickListener{
            override fun replaceActivity(year: String) {
                deliverData(year)
            }
        })

        typeAdapter.setOnItemClickListener(object :TypeViewHolder.OnItemClickListener{
            override fun replaceActivity(type: String) {
                deliverData(type)
            }
        })
    }

    private fun deliverData(data: String) {
        val token:String= intent.getStringExtra("token").toString()
        Log.d("moreactivity","token: $token")
        val intent = Intent(this, SearchResultActivity::class.java).apply {
            putExtra("result", data)
            putExtra("token",token)
        }
        Log.d("moreActivity", "clickedButton:$data")
        startActivity(intent)
    }

}
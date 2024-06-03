package com.softwareengineering.personalmovie.presentation.more

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.softwareengineering.personalmovie.data.Movie
import com.softwareengineering.personalmovie.databinding.ActivityMoreBinding
import com.softwareengineering.personalmovie.presentation.more.my.MyActivity
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
        val token:String= intent.getStringExtra("token").toString()
        genreAdapter= GenreAdapter()
        yearAdapter=YearAdapter()
        typeAdapter=TypeAdapter()
        with(binding){
            rvGenre.adapter = genreAdapter
            rvYears.adapter=yearAdapter
            rvType.adapter=typeAdapter
            btnMy.setOnClickListener{
                val intent = Intent(this@MoreActivity, MyActivity::class.java).apply {
                    putExtra("token",token)
                }
                startActivity(intent)
            }

        }
        genreAdapter.makeMenuList()
        yearAdapter.makeYearList()
        typeAdapter.makeTypeList()
        setClickListener(token)
    }

    private fun setClickListener(token:String) {
        // 아이템 클릭 리스너 설정
        genreAdapter.setOnItemClickListener(object : GenreViewHolder.OnItemClickListener {
            override fun replaceActivity(genre: Movie.Genre) {
                deliverData(genre.genre,token)
            }
        })

        yearAdapter.setOnItemClickListener(object :YearViewHolder.OnItemClickListener{
            override fun replaceActivity(year: String) {
                deliverData(year,token)
            }
        })

        typeAdapter.setOnItemClickListener(object :TypeViewHolder.OnItemClickListener{
            override fun replaceActivity(type: String) {
                deliverData(type,token)
            }
        })
    }

    private fun deliverData(data: String, token:String) {
        Log.d("moreactivity","token: $token")
        val intent = Intent(this, SearchResultActivity::class.java).apply {
            putExtra("result", data)
            putExtra("token",token)
        }
        Log.d("moreActivity", "clickedButton:$data")
        startActivity(intent)
    }

}
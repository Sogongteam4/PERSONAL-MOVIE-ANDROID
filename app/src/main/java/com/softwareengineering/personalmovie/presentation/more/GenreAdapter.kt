package com.softwareengineering.personalmovie.presentation.more

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softwareengineering.personalmovie.data.Movie
import com.softwareengineering.personalmovie.databinding.ItemGenreBinding

class GenreAdapter(
) : RecyclerView.Adapter<GenreViewHolder>() {
    //private val inflater by lazy { LayoutInflater.from(context) }
    private var genreList: MutableList<Movie.Genre> = mutableListOf()

    private lateinit var listener: GenreViewHolder.OnItemClickListener
    fun setOnItemClickListener(listener: GenreViewHolder.OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val binding = ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenreViewHolder(binding,listener)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val genreItem = genreList[position]
        holder.bind(genreItem, position)
    }

    override fun getItemCount() = genreList.size

    fun updateMenuList(newGenreList: List<Movie.Genre>) {
        genreList.clear()
        genreList.addAll(newGenreList)
        notifyDataSetChanged() // 모든 아이템을 업데이트
    }

    fun makeMenuList(){
        val list= listOf(
            Movie.Genre("어드벤처"),
            Movie.Genre("애니메이션"),
            Movie.Genre("어린이"),
            Movie.Genre("코미디"),
            Movie.Genre("로맨스"),//
            Movie.Genre("드라마"),
            Movie.Genre("액션"),
            Movie.Genre("범죄"),
            Movie.Genre("스릴러"),
            Movie.Genre("호러"),//
            Movie.Genre("공상과학"),
            Movie.Genre("미스테리"),
            Movie.Genre("뮤지컬"),
            Movie.Genre("전쟁"),
            Movie.Genre("IMAX"),//
            Movie.Genre("시대극"),
            Movie.Genre("누아르"),
            Movie.Genre("다큐멘터리"),
            Movie.Genre("판타지"),
        )
        genreList.addAll(list)
        notifyDataSetChanged()
    }
}
package com.softwareengineering.personalmovie.presentation.more.result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softwareengineering.personalmovie.data.Movie
import com.softwareengineering.personalmovie.data.responseDto.ResponseMovieDto
import com.softwareengineering.personalmovie.databinding.ItemResultBinding
import com.softwareengineering.personalmovie.presentation.more.GenreViewHolder

class SearchResultAdapter(
    private val resultList:List<ResponseMovieDto.Data>
) :RecyclerView.Adapter<SearchResultViewHolder>() {
    private lateinit var listener:SearchResultViewHolder.OnItemClickListener

    fun setOnItemClickListener(listener: SearchResultViewHolder.OnItemClickListener){
        this.listener=listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val binding=ItemResultBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SearchResultViewHolder(binding,listener)
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        val genreItem = resultList[position]
        holder.bind(genreItem)
    }

    override fun getItemCount() = resultList.size

}
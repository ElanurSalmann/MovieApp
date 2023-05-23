package com.elanursalmanzada.movieappexam.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elanursalmanzada.movieappexam.databinding.SearchItemsBinding
import com.elanursalmanzada.movieappexam.model.Movie

class SearchAdapter (var mContext: Context, var movieList:List<Movie>): RecyclerView.Adapter<SearchAdapter.SearchDesignHolder>() {
    inner class SearchDesignHolder(val binding: SearchItemsBinding): RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchDesignHolder {
        var binding=SearchItemsBinding.inflate(LayoutInflater.from(mContext))
        return SearchDesignHolder(binding)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: SearchDesignHolder, position: Int) {
        val b = holder.binding
        val list = movieList.get(position)

        b.title.text = list.title
        list.poster_path?.let { setImageWithGlide(it, b.imageSearch)}

    }
    fun setImageWithGlide(imageName : String,imageView: ImageView){
        val url = "https://image.tmdb.org/t/p/w500/$imageName"
        Glide.with(mContext).load(url).override(150,120).into(imageView)
    }

}
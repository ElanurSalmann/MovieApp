package com.elanursalmanzada.movieappexam.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elanursalmanzada.movieappexam.databinding.CardItemsBinding
import com.elanursalmanzada.movieappexam.model.Movie


class MovieAdapter(var mContext: Context, var movieList:List<Movie>):RecyclerView.Adapter<MovieAdapter.MovieDesignHolder>() {
    inner class MovieDesignHolder(val binding:CardItemsBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieDesignHolder {
        var binding=CardItemsBinding.inflate(LayoutInflater.from(mContext))
        return MovieDesignHolder(binding)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MovieDesignHolder, position: Int) {
        val b=holder.binding
        val list=movieList.get(position)

        b.movieName.text = list.title
        list.poster_path?.let { setImageWithGlide(it, b.posterImage)}

    }

    fun setImageWithGlide(imageName : String,imageView: ImageView){
        val url = "https://image.tmdb.org/t/p/w500/$imageName"
        Glide.with(mContext).load(url).override(150,120).into(imageView)
    }

}
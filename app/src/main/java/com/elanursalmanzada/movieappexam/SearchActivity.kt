package com.elanursalmanzada.movieappexam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.elanursalmanzada.movieappexam.adapter.MovieAdapter
import com.elanursalmanzada.movieappexam.adapter.SearchAdapter
import com.elanursalmanzada.movieappexam.databinding.ActivitySearchBinding
import com.elanursalmanzada.movieappexam.databinding.SearchItemsBinding
import com.elanursalmanzada.movieappexam.model.Movie
import com.elanursalmanzada.movieappexam.model.MovieResponse
import com.elanursalmanzada.movieappexam.retrofit.ApiUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var movieAdapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        movieAdapter = SearchAdapter(this, emptyList())
        binding.recyclerview.adapter = movieAdapter

        binding.buttonSearch.setOnClickListener {
            val query = binding.searchEditText.text.toString()
            if (query.isNotEmpty()) {
                performSearch(query)
            } else {
                Toast.makeText(this, "Please enter a search query", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun performSearch(query: String) {
        val apiKey = Constants.API_KEY
        val movieDao = ApiUtils.getMovieDao()
        val searchMoviesCall = movieDao.searchMovies(apiKey, query, page = 1)

        searchMoviesCall.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>,
                response: Response<MovieResponse>
            ) {
                if (response.isSuccessful) {
                    val movieResponse = response.body()
                    val movies = movieResponse?.results
                    movies?.let {
                        movieAdapter.movieList = it
                        movieAdapter.notifyDataSetChanged()
                    }
                } else {
                    Toast.makeText(
                        this@SearchActivity,
                        "Error: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Toast.makeText(
                    this@SearchActivity,
                    "Error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
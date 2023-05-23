package com.elanursalmanzada.movieappexam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.elanursalmanzada.movieappexam.adapter.MovieAdapter
import com.elanursalmanzada.movieappexam.databinding.ActivityMainBinding
import com.elanursalmanzada.movieappexam.model.MovieResponse
import com.elanursalmanzada.movieappexam.retrofit.ApiUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var movieAdapter = MovieAdapter(this, emptyList()) // Pass an empty list initially
        binding.recyclerview.adapter = movieAdapter
        var movieAdapter2 = MovieAdapter(this, emptyList()) // Pass an empty list initially

        binding.recyclerview1.adapter = movieAdapter2

        binding.buttonSearch.setOnClickListener {
            startActivity(Intent(this@MainActivity, SearchActivity::class.java))
        }
        val apiKey = Constants.API_KEY
        val movieDao = ApiUtils.getMovieDao()
        val upcomingMoviesCall = movieDao.getUpcomingMovies(apiKey, page = 1)
        val popularMoviesCall = movieDao.getPopularMovies(apiKey,page = 1)

        upcomingMoviesCall.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>,
                response: Response<MovieResponse>
            ) {
                if (response.isSuccessful) {
                    val movieResponse = response.body()
                    val movies = movieResponse?.results
                    movies?.let {
                        movieAdapter.movieList = it // Update the movieList in the adapter
                        movieAdapter.notifyDataSetChanged()
                    }
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Error: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    "Error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })


        popularMoviesCall.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>,
                response: Response<MovieResponse>
            ) {
                if (response.isSuccessful) {
                    val movieResponse = response.body()
                    val movies = movieResponse?.results
                    movies?.let {
                        movieAdapter2.movieList = it // Update the movieList in the adapter
                        movieAdapter2.notifyDataSetChanged()
                    }
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Error: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    "Error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}

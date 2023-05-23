package com.elanursalmanzada.movieappexam.retrofit

import com.elanursalmanzada.movieappexam.Constants

class ApiUtils {
    companion object{
        fun getMovieDao() : MovieDao {
            return RetrofitClient.getClient(Constants.BASE_URL).create(MovieDao::class.java)
        }
    }
}
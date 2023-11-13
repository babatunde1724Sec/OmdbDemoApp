package com.example.omdbdemoapp.rest

import com.example.omdbdemoapp.model.MoviesResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @GET(" ")
    fun getMovies(
        @Query("type") type: String, @Query("apikey") apiKey: String,
        @Query("s") search: String, @Query("page") page: Int
    ): Call<MoviesResponse>
}
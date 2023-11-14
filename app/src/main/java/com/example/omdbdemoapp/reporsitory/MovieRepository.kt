package com.example.omdbdemoapp.reporsitory

import com.example.omdbdemoapp.constants.Constants
import com.example.omdbdemoapp.model.Movie
import com.example.omdbdemoapp.model.MoviesResponse
import com.example.omdbdemoapp.rest.ApiClient
import com.example.omdbdemoapp.rest.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepository {

    fun getMoviesBySearch(search: String, page: Int, callback: RepositoryCallback<List<Movie>>) {
        val apiService = ApiClient.client.create(ApiInterface::class.java)
        val call = apiService.getMovies(Constants.TYPE_MOVIE, Constants.API_KEY, search, page)

        call.enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                try {
                    val responseBody = response.body()!!
                    val res = responseBody.results
                    if (!res.isNullOrEmpty()) {
                        callback.onSuccess(res)
                    } else {
                        callback.onError("")
                    }
                } catch (e: Exception) {
                    callback.onError(e.message.toString())
                }
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                callback.onError(t.message.toString())

            }
        })
    }

    interface RepositoryCallback<in T> {
        fun onSuccess(movieList: T?)
        fun onError(error: String)
    }
}
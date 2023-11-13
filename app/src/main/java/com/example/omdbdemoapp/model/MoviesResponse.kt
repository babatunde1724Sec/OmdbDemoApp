package com.example.omdbdemoapp.model

import com.example.omdbdemoapp.model.Movie
import com.google.gson.annotations.SerializedName

class MoviesResponse {
    @SerializedName("Search")
    var results: List<Movie>? = null

    @SerializedName("totalResults")
    var totalResults: Int = 0

    @SerializedName("Response")
    var response: Boolean = false
}
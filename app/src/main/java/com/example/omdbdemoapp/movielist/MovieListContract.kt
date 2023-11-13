package com.example.omdbdemoapp.movielist

import com.example.omdbdemoapp.base.BaseView
import com.example.omdbdemoapp.model.Movie
import com.example.omdbdemoapp.widget.EndlessRecyclerOnScrollListener

interface MovieListContract {
    interface ViewModel : BaseView<Presenter> {
        fun showMovies(movies: List<Movie>)
        fun showSnack(message: String)
        fun addEndlessListener(listener: EndlessRecyclerOnScrollListener)
    }

    interface Presenter {
        fun fetchMovieByName(search: String)
        fun addEndlessListener()
    }
}
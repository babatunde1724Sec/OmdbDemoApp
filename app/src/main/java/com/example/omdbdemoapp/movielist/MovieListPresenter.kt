package com.example.omdbdemoapp.movielist

import android.text.TextUtils
import com.example.omdbdemoapp.reporsitory.MovieRepository
import com.example.omdbdemoapp.model.Movie
import com.example.omdbdemoapp.widget.EndlessRecyclerOnScrollListener

class MovieListPresenter(private val mViewModel: MovieListContract.ViewModel, private val movieRepository: MovieRepository) :
    MovieListContract.Presenter {

    var mPage: Int = 1
    var mSearch: String = ""
    private var onLoadMoreListener: EndlessRecyclerOnScrollListener? = null

    override fun fetchMovieByName(search: String) {
        mSearch = search
        fetchMovies(mSearch, mPage)
    }

    override fun addEndlessListener() {
        addEndlessRecyclerOnScrollListener()
        mViewModel.addEndlessListener(onLoadMoreListener!!)
    }

    private fun addEndlessRecyclerOnScrollListener() {
        onLoadMoreListener = object : EndlessRecyclerOnScrollListener() {
            override fun onLoadMore() {
                mPage++
                fetchMovies(mSearch, mPage)
            }
        }
    }

    private fun fetchMovies(search: String, page: Int) {
        mViewModel.showLoading()
        movieRepository.getMoviesBySearch(search, page, object : MovieRepository.RepositoryCallback<List<Movie>> {
            override fun onSuccess(movieList: List<Movie>?) {
                mViewModel.hideLoading()
                mViewModel.showMovies(movieList!!)
            }
            override fun onError(error: String) {
                mViewModel.hideLoading()
                if(!TextUtils.isEmpty(error)){
                    mViewModel.showSnack(error)
                }
            }
        })
    }
}
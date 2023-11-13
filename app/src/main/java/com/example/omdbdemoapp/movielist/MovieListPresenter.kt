package com.example.omdbdemoapp.movielist

import com.example.omdbdemoapp.constants.Constants
import com.example.omdbdemoapp.model.Movie
import com.example.omdbdemoapp.rest.ApiClient
import com.example.omdbdemoapp.rest.ApiInterface
import com.example.omdbdemoapp.widget.EndlessRecyclerOnScrollListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.await

class MovieListPresenter(private val mViewModel: MovieListContractNew.ViewModel) :
    MovieListContractNew.Presenter {

    var mPage: Int = 1
    var mSearch: String = ""
    private var onLoadMoreListener: EndlessRecyclerOnScrollListener? = null
    private var mMovieList: List<Movie>? = null

    override fun fetchMovieByName(search: String) {
        mSearch = search
        getMoviesByType2(search, mPage)
    }

    override fun addEndlessListener() {
        addEndlessRecyclerOnScrollListener()
        mViewModel.addEndlessListener(onLoadMoreListener!!)
    }

    private fun addEndlessRecyclerOnScrollListener() {
        onLoadMoreListener = object : EndlessRecyclerOnScrollListener() {
            override fun onLoadMore() {
                mPage++
                getMoviesByType2(mSearch, mPage)
            }
        }
    }

    private fun getMoviesByType2(search: String, page: Int) {
        mViewModel.showLoading()
        val apiService = ApiClient.client.create(ApiInterface::class.java)
        GlobalScope.launch(Dispatchers.Main) {
            val response =
                apiService.getMovies(Constants.TYPE_MOVIE, Constants.API_KEY, search, page)
            try {
                val res = response.await()
                if (res.response) {
                    mMovieList = res.results
                    mViewModel.hideLoading()
                    mViewModel.showMovies(mMovieList!!)
                } else {
                    mViewModel.hideLoading()
                }
            } catch (e: Exception) {
                mViewModel.hideLoading()
                mViewModel.showSnack(e.message.toString())
            }
        }
    }
}
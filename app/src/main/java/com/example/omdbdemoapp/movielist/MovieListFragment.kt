package com.example.omdbdemoapp.movielist

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.omdbdemoapp.event.SearchEvent
import com.example.omdbdemoapp.R
import com.example.omdbdemoapp.constants.Constants
import com.example.omdbdemoapp.databinding.FragmentMovieBinding
import com.example.omdbdemoapp.model.Movie
import com.example.omdbdemoapp.utils.DialogUtil
import com.example.omdbdemoapp.widget.EndlessRecyclerOnScrollListener
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MovieListFragment : Fragment(), MovieListContract.ViewModel {
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!
    private var mListPresenter: MovieListPresenter? = null
    private var mListAdapter: MovieListAdapter? = null
    private var mMovie: ArrayList<Movie> = arrayListOf()
    private var savedTitle = Constants.DEFAULT_SEARCH

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: SearchEvent?) {
        if (event?.message != null) {
            _binding?.tvSearchTitle?.text = event.message.toString()
            mMovie.clear()
            mListPresenter?.addEndlessListener()
            mListPresenter?.mSearch = event.message.toString()
            savedTitle = event.message.toString()
            mListPresenter?.mPage = 1
            mListPresenter?.fetchMovieByName(event.message.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    private fun init() {
        mListPresenter = MovieListPresenter(this)
        if (!TextUtils.isEmpty(savedTitle)) {
            _binding?.tvSearchTitle?.text = savedTitle
        } else {
            _binding?.tvSearchTitle?.text = Constants.DEFAULT_SEARCH
        }
        mListPresenter?.fetchMovieByName(savedTitle)
        setUpAdapter()
        mListPresenter?.addEndlessListener()
    }

    private fun setUpAdapter() {
        mListAdapter = MovieListAdapter(mMovie, R.layout.item_list_movies)
        _binding?.rvMovies?.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        _binding?.rvMovies?.layoutManager = GridLayoutManager(context, 2)
        _binding?.rvMovies?.adapter = mListAdapter
    }

    override fun addEndlessListener(listener: EndlessRecyclerOnScrollListener) {
        _binding?.rvMovies?.addOnScrollListener(listener)
    }

    override fun showLoading() {
        _binding?.pbHeaderProgress?.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        _binding?.pbHeaderProgress?.visibility = View.GONE
    }

    override fun showMovies(movies: List<Movie>) {
        if (movies.isEmpty()) {
            Toast.makeText(context, getString(R.string.err_movie_list), Toast.LENGTH_LONG).show()
            return
        }
        mListAdapter?.addMovies(movies)
    }

    override fun showSnack(message: String) {
        _binding?.rvMovies?.let { DialogUtil.showSnackBar(it, message) }
    }
}
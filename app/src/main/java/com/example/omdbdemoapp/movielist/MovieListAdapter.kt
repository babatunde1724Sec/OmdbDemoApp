package com.example.omdbdemoapp.movielist

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.omdbdemoapp.R
import com.example.omdbdemoapp.model.Movie
import com.squareup.picasso.Picasso

class MovieListAdapter(
    private var movieList: ArrayList<Movie>,
    private val rowLayout: Int,
) : RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {

    class MovieViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var imageView: ImageView = v.findViewById(R.id.imageViewPoster) as ImageView
        internal var title: TextView = v.findViewById(R.id.textViewTitle) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(rowLayout, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentMovie = movieList[position]
        if (!TextUtils.isEmpty(currentMovie.poster)) {
            Picasso.get().load(currentMovie.poster)
                .placeholder(R.drawable.poster_placeholder)
                .into(holder.imageView)
        }
        if (!TextUtils.isEmpty(currentMovie.title)) {
            holder.title.text = currentMovie.title
        }

        holder.imageView.setOnClickListener {
            // TODO: Handle open details screen or other if needed
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun addMovies(movies: List<Movie>) {
        movieList.addAll(movies)
        notifyDataSetChanged()
    }
}
package com.example.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core.R
import com.example.core.databinding.MovieItemRowBinding
import com.example.core.domain.model.Movie

class MovieAdapter: RecyclerView.Adapter<MovieAdapter.MovieHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback
    private val movies: ArrayList<Movie> = ArrayList()

    fun setMovie(list: ArrayList<Movie>){
        movies.clear()
        movies.addAll(list)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class MovieHolder(private val binding: MovieItemRowBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie){
            binding.movieTitle.text = movie.title
            binding.movieDesc.text = movie.overview

            if(movie.poster_path == ""){
                Glide.with(itemView)
                    .load(R.drawable.no_preview)
                    .fitCenter()
                    .into(binding.imagePoster)
            }else{
                Glide.with(itemView)
                    .load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
                    .centerCrop()
                    .into(binding.imagePoster)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val binding = MovieItemRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.bind(movies[position])
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(movies[position].id) }
    }

    override fun getItemCount(): Int = movies.size

    interface OnItemClickCallback {
        fun onItemClicked(data: Int)
    }
}
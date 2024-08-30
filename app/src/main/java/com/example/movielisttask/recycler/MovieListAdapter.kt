package com.example.movielisttask.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movielisttask.R
import com.example.movielisttask.databinding.MovieItemBinding
import com.example.movielisttask.domain.model.Movie

class MovieListAdapter :
    ListAdapter<Movie, MovieListAdapter.MovieViewHolder>(MovieDiffUtilItemCallback()) {
    class MovieViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            with(binding) {
                moviePoster.load(movie.posterUrl)
                movieTitle.text = movie.nameRu
                favoriteIcon.setImageResource(defineFavoriteIcon(movie.isFavorite))

                root.setOnFocusChangeListener { _, isFocused ->
                    if (isFocused) {
                        moviePoster.scaleX = 1.1f
                        moviePoster.scaleY = 1.1f
                    } else {
                        moviePoster.scaleX = 1f
                        moviePoster.scaleY = 1f
                    }
                }

                root.setOnClickListener {
                    movie.onClick()
                }

                favoriteIcon.setOnClickListener {
                    movie.onFavoriteClick()
                    favoriteIcon.setImageResource(defineFavoriteIcon(movie.isFavorite))
                }
            }
        }

        private fun defineFavoriteIcon(isFavorite: Boolean) =
            if (isFavorite) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieItemBinding.inflate(inflater, parent, false)

        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: MovieViewHolder, position: Int) {
        val movie = currentList[position]
        viewHolder.bind(movie)
    }

    companion object {
        private const val SCALE_VALUE = 25
    }
}
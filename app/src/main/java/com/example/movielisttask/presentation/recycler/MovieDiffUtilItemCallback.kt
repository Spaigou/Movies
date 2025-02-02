package com.example.movielisttask.presentation.recycler

import androidx.recyclerview.widget.DiffUtil
import com.example.feature_movies_api.domain.model.Movie

class MovieDiffUtilItemCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.kinopoiskId == newItem.kinopoiskId
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}
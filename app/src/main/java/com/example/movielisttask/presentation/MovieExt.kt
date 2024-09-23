package com.example.movielisttask.presentation

import com.example.feature_movies_api.domain.model.Movie

fun Movie.onFavoriteClick() {
    isFavorite = !isFavorite
}

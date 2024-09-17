package com.example.feature_movies_api.domain.model

fun Movie.onFavoriteClick() {
    isFavorite = !isFavorite
}

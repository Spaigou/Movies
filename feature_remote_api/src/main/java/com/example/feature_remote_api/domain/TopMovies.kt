package com.example.feature_remote_api.domain

import com.example.feature_movies_api.domain.model.Movie

data class TopMovies(
    val total: Int,
    val totalPages: Int,
    val items: List<Movie>,
)

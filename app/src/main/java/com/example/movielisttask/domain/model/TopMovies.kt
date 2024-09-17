package com.example.movielisttask.domain.model

data class TopMovies(
    val total: Int,
    val totalPages: Int,
    val items: List<Movie>,
)
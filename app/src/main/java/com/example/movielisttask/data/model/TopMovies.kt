package com.example.movielisttask.data.model


data class TopMovies(
    val total: Int,
    val totalPages: Int,
    val items: List<Movie>,
)
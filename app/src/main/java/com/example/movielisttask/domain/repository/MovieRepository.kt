package com.example.movielisttask.domain.repository

import com.example.movielisttask.data.model.Movie

interface MovieRepository {
    suspend fun getMoviesCollection(type: String, page: Int): List<Movie>?

    suspend fun getMovieById(movieId: Int): Movie?
}
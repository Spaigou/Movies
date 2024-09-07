package com.example.movielisttask.domain.repository

import com.example.movielisttask.data.model.Movie

interface LocalMoviesRepository {
    suspend fun saveMovies(movies: List<Movie>)
    suspend fun getMovies(): List<Movie>
}
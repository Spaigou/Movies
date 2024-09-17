package com.example.movielisttask.domain.repository

import com.example.movielisttask.data.model.Genre
import com.example.movielisttask.domain.model.Movie

interface LocalMoviesRepository {
    suspend fun saveMovies(movies: List<com.example.movielisttask.domain.model.Movie>)
    suspend fun getMovies(): List<com.example.movielisttask.domain.model.Movie>
    suspend fun getMovies(genre: Genre): List<com.example.movielisttask.domain.model.Movie>
}

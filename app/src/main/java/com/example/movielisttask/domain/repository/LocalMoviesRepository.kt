package com.example.movielisttask.domain.repository

import com.example.movielisttask.domain.model.Genre
import com.example.movielisttask.domain.model.Movie

interface LocalMoviesRepository {

    suspend fun saveMovies(movies: List<Movie>)

    suspend fun getMovies(): List<Movie>

    suspend fun getMovies(genre: Genre): List<Movie>

    suspend fun updateFavorite(movie: Movie)
}

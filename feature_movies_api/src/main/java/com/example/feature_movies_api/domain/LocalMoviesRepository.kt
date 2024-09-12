package com.example.feature_movies_api.domain

interface LocalMoviesRepository {
    suspend fun saveMovies(movies: List<Movie>)
    suspend fun getMovies(): List<Movie>
    suspend fun getMovies(genre: Genre): List<Movie>
}

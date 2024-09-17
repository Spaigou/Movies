package com.example.feature_movies_api.domain

import com.example.feature_movies_api.domain.model.Genre
import com.example.feature_movies_api.domain.model.Movie

interface LocalMoviesRepository {

    suspend fun saveMovies(movies: List<Movie>)

    suspend fun getMovies(): List<Movie>

    suspend fun getMovies(genre: Genre): List<Movie>

    suspend fun updateFavorite(movie: Movie)
}

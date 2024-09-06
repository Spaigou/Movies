package com.example.movielisttask.domain.repository

import com.example.movielisttask.data.model.Movie

interface FavoritesRepository {
    suspend fun saveFavoriteMovies(movies: List<Movie>)
    suspend fun getFavoriteMovies(): List<Movie>
}
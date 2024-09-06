package com.example.movielisttask.domain.usecase

import android.content.SharedPreferences
import com.example.movielisttask.data.model.Movie
import com.example.movielisttask.data.repository.SharedPreferencesFavoritesRepository
import com.example.movielisttask.domain.repository.FavoritesRepository

class GetFavoriteMoviesUseCase(private val favoritesRepository: FavoritesRepository) {
    suspend operator fun invoke(): List<Movie> {
        return favoritesRepository.getFavoriteMovies()
    }
}
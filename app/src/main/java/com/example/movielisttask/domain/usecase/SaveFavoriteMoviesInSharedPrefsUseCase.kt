package com.example.movielisttask.domain.usecase

import com.example.movielisttask.data.model.Movie
import com.example.movielisttask.domain.repository.FavoritesRepository

class SaveFavoriteMoviesUseCase(private val favoritesRepository: FavoritesRepository) {
    suspend operator fun invoke(movies: List<Movie>) {
        return favoritesRepository.saveFavoriteMovies(movies)
    }
}
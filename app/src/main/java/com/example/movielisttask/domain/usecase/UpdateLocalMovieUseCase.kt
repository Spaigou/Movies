package com.example.movielisttask.domain.usecase

import com.example.movielisttask.domain.model.Movie
import com.example.movielisttask.domain.repository.LocalMoviesRepository

class UpdateLocalFavoriteUseCase(private val localMoviesRepository: LocalMoviesRepository) {
    suspend operator fun invoke(movie: Movie) {
        return localMoviesRepository.updateFavorite(movie)
    }
}
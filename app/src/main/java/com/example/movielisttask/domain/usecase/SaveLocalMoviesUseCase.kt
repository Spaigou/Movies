package com.example.movielisttask.domain.usecase

import com.example.movielisttask.domain.model.Movie
import com.example.movielisttask.domain.repository.LocalMoviesRepository

class SaveLocalMoviesUseCase(private val localMoviesRepository: LocalMoviesRepository) {
    suspend operator fun invoke(movies: List<com.example.movielisttask.domain.model.Movie>) {
        return localMoviesRepository.saveMovies(movies)
    }
}
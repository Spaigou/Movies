package com.example.movielisttask.domain.usecase

import com.example.movielisttask.data.model.Movie
import com.example.movielisttask.domain.repository.LocalMoviesRepository

class GetLocalMoviesUseCase(private val localMoviesRepository: LocalMoviesRepository) {
    suspend operator fun invoke(): List<Movie> {
        return localMoviesRepository.getMovies()
    }
}
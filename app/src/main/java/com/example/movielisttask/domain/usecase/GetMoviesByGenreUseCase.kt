package com.example.movielisttask.domain.usecase

import com.example.movielisttask.data.model.Genre
import com.example.movielisttask.data.model.Movie
import com.example.movielisttask.domain.repository.LocalMoviesRepository

class GetMoviesByGenreUseCase(private val localMoviesRepository: LocalMoviesRepository) {
    suspend operator fun invoke(genre: Genre): List<Movie> {
        return localMoviesRepository.getMovies(genre)
    }
}
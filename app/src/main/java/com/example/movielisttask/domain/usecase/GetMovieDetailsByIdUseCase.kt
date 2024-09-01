package com.example.movielisttask.domain.usecase

import com.example.movielisttask.data.repository.MovieRepositoryImpl
import com.example.movielisttask.data.model.MovieDetails

class GetMovieDetailsByIdUseCase {
    private val repository = MovieRepositoryImpl()

    suspend operator fun invoke(movieId: Int): MovieDetails? {
        val movie = repository.getMovieById(movieId)
        return movie
    }
}
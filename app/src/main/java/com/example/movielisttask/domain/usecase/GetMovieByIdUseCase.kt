package com.example.movielisttask.domain.usecase

import com.example.movielisttask.data.model.Movie
import com.example.movielisttask.data.repository.MovieRepositoryImpl

class GetMovieByIdUseCase {
    private val repository = MovieRepositoryImpl()

    suspend operator fun invoke(movieId: Int): Movie? {
        val movie = repository.getMovieById(movieId)
        return movie
    }
}
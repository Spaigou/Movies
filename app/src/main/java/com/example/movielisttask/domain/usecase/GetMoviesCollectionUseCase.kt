package com.example.movielisttask.domain.usecase

import com.example.movielisttask.common.MoviesCollectionType
import com.example.movielisttask.data.repository.MoviesRepositoryImpl
import com.example.movielisttask.data.model.Movie
import com.example.movielisttask.domain.repository.MoviesRepository

class GetMoviesCollectionUseCase(private val moviesRepository: MoviesRepository) {

    suspend operator fun invoke(type: String = MoviesCollectionType.TOP_POPULAR_MOVIES.name, page: Int = 1): List<Movie>? {
        val movies = moviesRepository.getMoviesCollection(type, page)
        return movies
    }
}
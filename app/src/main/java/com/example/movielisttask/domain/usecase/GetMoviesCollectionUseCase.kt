package com.example.movielisttask.domain.usecase

import com.example.movielisttask.common.MoviesCollectionType
import com.example.movielisttask.data.repository.MovieRepositoryImpl
import com.example.movielisttask.data.model.Movie

class GetMoviesCollectionUseCase {
    private val repository = MovieRepositoryImpl()

    suspend operator fun invoke(type: String = MoviesCollectionType.TOP_POPULAR_ALL.name, page: Int = 1): List<Movie>? {
        val movies = repository.getMoviesCollection(type, page)
        return movies
    }
}
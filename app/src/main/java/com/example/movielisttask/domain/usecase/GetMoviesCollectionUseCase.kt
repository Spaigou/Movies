package com.example.movielisttask.domain.usecase

import com.example.movielisttask.common.MoviesCollectionType
import com.example.movielisttask.domain.model.Movie
import com.example.movielisttask.domain.repository.RemoteMoviesRepository

class GetMoviesCollectionUseCase(private val remoteMoviesRepository: RemoteMoviesRepository) {

    suspend operator fun invoke(type: String = MoviesCollectionType.TOP_POPULAR_MOVIES.name, page: Int = 1): List<com.example.movielisttask.domain.model.Movie>? {
        return remoteMoviesRepository.getMoviesCollection(type, page)
    }
}
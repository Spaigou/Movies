package com.example.movielisttask.domain.usecase

import com.example.movielisttask.data.model.Filters
import com.example.movielisttask.domain.repository.RemoteMoviesRepository

class GetFiltersUseCase(private val remoteMoviesRepository: RemoteMoviesRepository) {
    suspend operator fun invoke(): Filters? {
        return remoteMoviesRepository.getFilters()
    }
}
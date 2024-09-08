package com.example.movielisttask.domain.usecase

import com.example.movielisttask.data.model.Filters
import com.example.movielisttask.data.repository.MoviesRepositoryImpl
import com.example.movielisttask.domain.repository.MoviesRepository

class GetFiltersUseCase(private val moviesRepository: MoviesRepository) {
    suspend operator fun invoke(): Filters? {
        return moviesRepository.getFilters()
    }
}
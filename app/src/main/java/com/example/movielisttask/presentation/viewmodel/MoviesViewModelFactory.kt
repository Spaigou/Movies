package com.example.movielisttask.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.feature_movies_api.domain.LocalMoviesRepository
import com.example.movielisttask.domain.repository.RemoteMoviesRepository

@Suppress("UNCHECKED_CAST")
class MoviesViewModelFactory(
    private val localMoviesRepository: LocalMoviesRepository,
    private val remoteMoviesRepository: RemoteMoviesRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MoviesViewModel(localMoviesRepository, remoteMoviesRepository) as T
    }
}
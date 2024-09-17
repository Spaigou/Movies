package com.example.movielisttask.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.feature_movies_api.domain.LocalMoviesRepository
import com.example.feature_remote_api.domain.RemoteMoviesRepository

@Suppress("UNCHECKED_CAST")
class MoviesViewModelFactory(
    private val localMoviesRepository: LocalMoviesRepository,
    private val remoteMoviesRepository: com.example.feature_remote_api.domain.RemoteMoviesRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MoviesViewModel(localMoviesRepository, remoteMoviesRepository) as T
    }
}
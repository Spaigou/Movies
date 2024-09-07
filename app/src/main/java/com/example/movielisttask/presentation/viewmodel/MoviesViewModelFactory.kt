package com.example.movielisttask.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movielisttask.domain.repository.LocalMoviesRepository

@Suppress("UNCHECKED_CAST")
class MoviesViewModelFactory(
    private val localMoviesRepository: LocalMoviesRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MoviesViewModel(localMoviesRepository) as T
    }
}
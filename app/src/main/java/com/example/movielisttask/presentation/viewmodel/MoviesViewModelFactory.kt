package com.example.movielisttask.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movielisttask.domain.repository.FavoritesRepository

@Suppress("UNCHECKED_CAST")
class MoviesViewModelFactory(
    private val favoritesRepository: FavoritesRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MoviesViewModel(favoritesRepository) as T
    }
}
package com.example.movielisttask.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movielisttask.data.model.Movie
import com.example.movielisttask.domain.usecase.GetMovieByIdUseCase
import com.example.movielisttask.domain.usecase.GetMoviesCollectionUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MoviesViewModel : ViewModel() {
    private val _movies = MutableStateFlow(emptyList<Movie>())
    val movies: StateFlow<List<Movie>> = _movies

    private val _selectedMovie = MutableStateFlow<Movie?>(null)
    val selectedMovie: StateFlow<Movie?> = _selectedMovie

    private val getMoviesCollection = GetMoviesCollectionUseCase()
    private val getMovieById = GetMovieByIdUseCase()

    init {
        viewModelScope.launch {
            delay(1000L)
            do {
                val fetchedMovies = getMoviesCollection()
                if (fetchedMovies != null) {
                    _movies.value = fetchedMovies.onEach { it.onClick = { onMovieClicked(it.kinopoiskId) } }
                } else {
                    Log.d(TAG, "Error fetching movies")
                    delay(5000L)
                }
            } while (fetchedMovies == null)

        }
    }

    private fun onMovieClicked(id: Int) {
        // костыль
        if (_selectedMovie.value != null) _selectedMovie.value = null
        viewModelScope.launch {
            _selectedMovie.value = getMovieById(id)
        }
    }

    companion object {
        private const val TAG = "MoviesViewModel"
    }
}

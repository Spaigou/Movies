package com.example.movielisttask.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movielisttask.data.model.Movie
import com.example.movielisttask.data.model.MovieDetails
import com.example.movielisttask.domain.usecase.GetMovieDetailsByIdUseCase
import com.example.movielisttask.domain.usecase.GetMoviesCollectionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MoviesViewModel : ViewModel() {
    private val _movies = MutableStateFlow(emptyList<Movie>())
    val movies: StateFlow<List<Movie>> = _movies

    private val _selectedMovie = MutableStateFlow<MovieDetails?>(null)
    val selectedMovie: StateFlow<MovieDetails?> = _selectedMovie

    private val getMoviesCollection = GetMoviesCollectionUseCase()
    private val getMovieDetails = GetMovieDetailsByIdUseCase()

    init {
        viewModelScope.launch {
            val fetchedMovies = getMoviesCollection()
            if (fetchedMovies != null) {
                _movies.value = fetchedMovies.onEach { it.onClick = { onMovieClicked(it.kinopoiskId) } }
            } else {
                Log.d(TAG, "Error fetching movies")
            }
        }
    }

    private fun onMovieClicked(id: Int) {
        // костыль
        if (_selectedMovie.value != null) _selectedMovie.value = null
        viewModelScope.launch {
            _selectedMovie.value = getMovieDetails(id)
        }
    }

    companion object {
        private const val TAG = "MoviesViewModel"
    }
}

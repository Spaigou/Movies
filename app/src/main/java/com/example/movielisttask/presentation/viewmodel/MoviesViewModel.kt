package com.example.movielisttask.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movielisttask.data.model.Movie
import com.example.movielisttask.data.model.onFavoriteClick
import com.example.movielisttask.domain.repository.LocalMoviesRepository
import com.example.movielisttask.domain.usecase.GetLocalMoviesUseCase
import com.example.movielisttask.domain.usecase.GetMoviesCollectionUseCase
import com.example.movielisttask.domain.usecase.SaveLocalMoviesUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MoviesViewModel(
    localMoviesRepository: LocalMoviesRepository,
) : ViewModel() {
    private val _movies = MutableStateFlow(emptyList<Movie>())
    val movies: StateFlow<List<Movie>> = _movies

    private val _selectedMovie = MutableStateFlow<Movie?>(null)
    val selectedMovie: StateFlow<Movie?> = _selectedMovie

    private val getMoviesCollection = GetMoviesCollectionUseCase()
    private val getLocalMovies = GetLocalMoviesUseCase(localMoviesRepository)
    private val saveLocalMovies = SaveLocalMoviesUseCase(localMoviesRepository)

    init {
        viewModelScope.launch {
            delay(700L)  // для scroll bar'а
            val localMovies = getLocalMovies()
            if (localMovies.isNotEmpty()) {
                _movies.value = localMovies
            } else {
                fetchMovies()
            }
        }
    }

    private suspend fun fetchMovies() {
        do {
            Log.d(TAG, "Fetching movies...")
            val fetchedMovies = getMoviesCollection()
            if (fetchedMovies != null) {
                _movies.value = fetchedMovies
            } else {
                Log.d(TAG, "Error fetching movies")
                delay(5000L)
            }
        } while (fetchedMovies == null)
    }

    fun onMovieClicked(id: Int) {
        viewModelScope.launch {
            _selectedMovie.value = _movies.value.first { it.kinopoiskId == id }
        }
    }

    fun onFavoriteClicked(movie: Movie) {
        movie.onFavoriteClick()
    }

    fun onBackPressed() {
        _selectedMovie.value = null
    }

    fun onStopCalled() {
        viewModelScope.launch {
            saveLocalMovies(_movies.value)
        }
    }

    companion object {
        private const val TAG = "MoviesViewModel"
    }
}

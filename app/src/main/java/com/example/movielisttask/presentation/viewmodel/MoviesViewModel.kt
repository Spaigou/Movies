package com.example.movielisttask.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movielisttask.R
import com.example.movielisttask.data.model.Genre
import com.example.movielisttask.data.model.Movie
import com.example.movielisttask.domain.repository.LocalMoviesRepository
import com.example.movielisttask.domain.repository.MoviesRepository
import com.example.movielisttask.domain.usecase.GetFiltersUseCase
import com.example.movielisttask.domain.usecase.GetLocalMoviesUseCase
import com.example.movielisttask.domain.usecase.GetMoviesByGenreUseCase
import com.example.movielisttask.domain.usecase.GetMoviesCollectionUseCase
import com.example.movielisttask.domain.usecase.SaveLocalMoviesUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MoviesViewModel(
    localMoviesRepository: LocalMoviesRepository,
    moviesRepository: MoviesRepository,
) : ViewModel() {
    private val _displayMovies = MutableStateFlow(emptyList<Movie>())
    val displayMovies: StateFlow<List<Movie>> = _displayMovies

    private val _movies = MutableStateFlow(emptyList<Movie>())
    val movies: StateFlow<List<Movie>> = _movies

    private val _favorites = MutableStateFlow<List<Movie>?>(null)
    val favorites: StateFlow<List<Movie>?> = _favorites

    private val _genreMovies = MutableStateFlow<List<Movie>?>(null)
    val genreMovies: StateFlow<List<Movie>?> = _genreMovies

    private val _selectedMovie = MutableStateFlow<Movie?>(null)
    val selectedMovie: StateFlow<Movie?> = _selectedMovie

    private val _genres = MutableStateFlow(emptyList<Genre>())
    val genres: StateFlow<List<Genre>> = _genres

    private val getMoviesCollection = GetMoviesCollectionUseCase(moviesRepository)
    private val getFilters = GetFiltersUseCase(moviesRepository)
    private val getLocalMovies = GetLocalMoviesUseCase(localMoviesRepository)
    private val saveLocalMovies = SaveLocalMoviesUseCase(localMoviesRepository)
    private val getMoviesByGenreUseCase = GetMoviesByGenreUseCase(localMoviesRepository)

    init {
        viewModelScope.launch {
            delay(700L)  // для scroll bar'а
            val localMovies = getLocalMovies()
            fetchFilters()
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

    private suspend fun fetchFilters() {
        do {
            Log.d(TAG, "Fetching filters...")
            val fetchedFilters = getFilters()
            if (fetchedFilters != null) {
                _genres.value = fetchedFilters.genres
            } else {
                Log.d(TAG, "Error fetching movies")
                delay(5000L)
            }
        } while (fetchedFilters == null)
    }

    fun onMovieClicked(id: Int) {
        viewModelScope.launch {
            _selectedMovie.value = _movies.value.first { it.kinopoiskId == id }
        }
    }

    fun onBackPressed() {
        _selectedMovie.value = null
    }

    fun onStopCalled() {
        viewModelScope.launch {
            saveLocalMovies(_movies.value)
        }
    }

    fun onMenuItemClicked(itemId: Int) {
        if (itemId == R.id.movies) {
            _favorites.value = null
        }
        if (itemId == R.id.favorites) {
            _favorites.value = _movies.value.filter { it.isFavorite }
        }
    }

    fun onNoneGenreSelected() {
        _genreMovies.value = null
    }

    fun onGenreSelected(position: Int) {
        viewModelScope.launch {
            _genreMovies.value = getMoviesByGenreUseCase(genres.value[position])
        }
    }

    companion object {
        private const val TAG = "MoviesViewModel"
    }
}

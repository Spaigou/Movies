package com.example.movielisttask.presentation.viewmodel

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movielisttask.data.model.Movie
import com.example.movielisttask.domain.repository.FavoritesRepository
import com.example.movielisttask.domain.usecase.GetFavoriteMoviesUseCase
import com.example.movielisttask.domain.usecase.GetMovieByIdUseCase
import com.example.movielisttask.domain.usecase.GetMoviesCollectionUseCase
import com.example.movielisttask.domain.usecase.SaveFavoriteMoviesUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MoviesViewModel(
    favoritesRepository: FavoritesRepository,
) : ViewModel() {
    private val _movies = MutableStateFlow(emptyList<Movie>())
    val movies: StateFlow<List<Movie>> = _movies

    private val _selectedMovie = MutableStateFlow<Movie?>(null)
    val selectedMovie: StateFlow<Movie?> = _selectedMovie

    private val getMoviesCollection = GetMoviesCollectionUseCase()
    private val getMovieById = GetMovieByIdUseCase()
    private val getFavoriteMoviesFromSharedPrefs = GetFavoriteMoviesUseCase(favoritesRepository)
    private val saveFavoriteMoviesInSharedPrefs = SaveFavoriteMoviesUseCase(favoritesRepository)

    init {
        viewModelScope.launch {
            val favorites = getFavoriteMoviesFromSharedPrefs()
            delay(700L)  // для scroll bar'а
            do {
                val fetchedMovies = getMoviesCollection()
                if (fetchedMovies != null) {
                    _movies.value = fetchedMovies.onEach { movie ->
                        if (favorites.any { it.kinopoiskId == movie.kinopoiskId })
                            movie.isFavorite = true
                    }
                } else {
                    Log.d(TAG, "Error fetching movies")
                    delay(5000L)
                }
            } while (fetchedMovies == null)

        }
    }

    fun onMovieClicked(id: Int) {
        viewModelScope.launch {
            _selectedMovie.value = getMovieById(id)
        }
    }

    fun onFavoriteClicked(movie: Movie) {
        movie.onFavoriteClick()
    }

    fun onBackPressed() {
        _selectedMovie.value = null
    }

    fun onStartCalled() {
        viewModelScope.launch {
            val favorites = getFavoriteMoviesFromSharedPrefs()
            _movies.value.onEach {
                Log.d(TAG, "${it.nameRu}: ${favorites.contains(it)}")
                if (favorites.contains(it)) it.isFavorite = true
            }
        }
    }

    fun onStopCalled() {
        viewModelScope.launch {
            val favorites = _movies.value.filter { it.isFavorite }
            saveFavoriteMoviesInSharedPrefs(favorites)
        }
    }

    companion object {
        private const val TAG = "MoviesViewModel"
    }
}

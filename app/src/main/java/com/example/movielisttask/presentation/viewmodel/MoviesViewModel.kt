package com.example.movielisttask.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature_movies_api.domain.LocalMoviesRepository
import com.example.feature_movies_api.domain.model.Genre
import com.example.feature_movies_api.domain.model.Movie
import com.example.feature_movies_api.domain.model.onFavoriteClick
import com.example.movielisttask.R
import com.example.movielisttask.domain.repository.RemoteMoviesRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val localMoviesRepository: LocalMoviesRepository,
    private val remoteMoviesRepository: RemoteMoviesRepository,
) : ViewModel() {
    private var movies = emptyList<Movie>()
    private var genreMovies = emptyList<Movie>()

    private val _displayMovies = MutableStateFlow(emptyList<Movie>())
    val displayMovies: StateFlow<List<Movie>> = _displayMovies

    private val _selectedMovie = MutableStateFlow<Movie?>(null)
    val selectedMovie: StateFlow<Movie?> = _selectedMovie

    private val _genres = MutableStateFlow(emptyList<Genre>())
    val genres: StateFlow<List<Genre>> = _genres

    init {
        viewModelScope.launch {
            delay(700L)  // для scroll bar'а
            val localMovies = localMoviesRepository.getMovies()
            if (localMovies.isNotEmpty()) {
                movies = localMovies
                _displayMovies.value = localMovies
            } else {
                fetchMovies()
            }
            getGenres()
        }
    }

    private suspend fun fetchMovies() {
        do {
            Log.d(TAG, "Fetching movies...")
            val fetchedMovies = remoteMoviesRepository.getMoviesCollection()
            if (fetchedMovies != null) {
                movies = fetchedMovies
                _displayMovies.value = fetchedMovies
                localMoviesRepository.saveMovies(movies)
            } else {
                Log.d(TAG, "Error fetching movies")
                delay(5000L)
            }
        } while (fetchedMovies == null)
    }

    private fun getGenres() {
        val localGenres = mutableListOf<Genre>()
        localGenres.add(Genre("Все"))
        movies .forEach { movie ->
            movie.genres.forEach { localGenres.add(it) }
        }
        localGenres.distinct()
        _genres.value = localGenres
    }

    fun onMovieClicked(movie: Movie) {
        _selectedMovie.value = movie
    }

    fun onFavoriteClicked(movie: Movie) {
        viewModelScope.launch {
            movie.onFavoriteClick()
            localMoviesRepository.updateFavorite(movie)
        }
    }

    fun onBackPressed() {
        _selectedMovie.value = null
    }

    fun onMenuItemClicked(itemId: Int) {
        if (itemId == R.id.movies) {
            _displayMovies.value = genreMovies.ifEmpty { movies }
        }
        if (itemId == R.id.favorites) {
            val toDisplay = genreMovies.ifEmpty { movies }
            _displayMovies.value = toDisplay.filter { it.isFavorite }
        }
    }

    fun onNoneGenreSelected() {
        _displayMovies.value = movies
    }

    fun onGenreSelected(position: Int) {
        viewModelScope.launch {
            genreMovies = if (genres.value[position].genre != "Все")
                localMoviesRepository.getMovies(genres.value[position])
            else
                movies
            _displayMovies.value = genreMovies
        }
    }


    companion object {
        private const val TAG = "MoviesViewModel"
    }
}

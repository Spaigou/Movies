package com.example.movielisttask.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movielisttask.R
import com.example.movielisttask.domain.model.Movie
import com.example.movielisttask.data.model.onFavoriteClick
import com.example.movielisttask.domain.model.Genre
import com.example.movielisttask.domain.repository.LocalMoviesRepository
import com.example.movielisttask.domain.repository.RemoteMoviesRepository
import com.example.movielisttask.domain.usecase.GetLocalMoviesUseCase
import com.example.movielisttask.domain.usecase.GetMoviesByGenreUseCase
import com.example.movielisttask.domain.usecase.GetMoviesCollectionUseCase
import com.example.movielisttask.domain.usecase.SaveLocalMoviesUseCase
import com.example.movielisttask.domain.usecase.UpdateLocalFavoriteUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MoviesViewModel(
    localMoviesRepository: LocalMoviesRepository,
    remoteMoviesRepository: RemoteMoviesRepository,
) : ViewModel() {
    private var movies = emptyList<Movie>()
    private var genreMovies = emptyList<Movie>()

    private val _displayMovies = MutableStateFlow(emptyList<Movie>())
    val displayMovies: StateFlow<List<Movie>> = _displayMovies

    private val _selectedMovie = MutableStateFlow<Movie?>(null)
    val selectedMovie: StateFlow<Movie?> = _selectedMovie

    private val _genres = MutableStateFlow(emptyList<Genre>())
    val genres: StateFlow<List<Genre>> = _genres

    private val getMoviesCollection = GetMoviesCollectionUseCase(remoteMoviesRepository)
    private val getLocalMovies = GetLocalMoviesUseCase(localMoviesRepository)
    private val saveLocalMovies = SaveLocalMoviesUseCase(localMoviesRepository)
    private val updateLocalFavorite = UpdateLocalFavoriteUseCase(localMoviesRepository)
    private val getMoviesByGenreUseCase = GetMoviesByGenreUseCase(localMoviesRepository)

    init {
        viewModelScope.launch {
            delay(700L)  // для scroll bar'а
            val localMovies = getLocalMovies()
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
            val fetchedMovies = getMoviesCollection()
            if (fetchedMovies != null) {
                movies = fetchedMovies
                _displayMovies.value = fetchedMovies
                saveLocalMovies(movies)
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
            updateLocalFavorite(movie)
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
                getMoviesByGenreUseCase(genres.value[position])
            else
                movies
            _displayMovies.value = genreMovies
        }
    }


    companion object {
        private const val TAG = "MoviesViewModel"
    }
}

package com.example.movielisttask

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movielisttask.domain.model.Movie
import com.example.movielisttask.domain.model.MoviesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    private val _movies = MutableStateFlow(emptyList<Movie>())
    val movies: StateFlow<List<Movie>> = _movies

    private val _selectedMovie = MutableStateFlow<Movie?>(null)
    val selectedMovie: StateFlow<Movie?> = _selectedMovie

    private val repository = MoviesRepository()

    init {
        viewModelScope.launch {
            do {
                val fetchedMovies = repository.getTopMovies()
                if (fetchedMovies != null) {
                    _movies.value = fetchedMovies.map { movie ->
                        movie.apply {
                            onClick = { onMovieClicked(movie.kinopoiskId) }
                        }
                    }
                } else {
                    Log.d(TAG, "Error fetching movies")
                }
            } while (fetchedMovies == null)
        }
    }

    private fun onMovieClicked(id: Int) {
        // костыль
        if (_selectedMovie.value != null) _selectedMovie.value = null
//        _selectedMovie.value = _movies.value.firstOrNull { it.kinopoiskId == id }
        viewModelScope.launch {
            _selectedMovie.value = repository.getMovie(id)
        }
    }

    companion object {
        private const val TAG = "MovieViewModel"
    }
}

package com.example.feature_movies_prefs_impl.domain

import android.content.SharedPreferences
import com.example.feature_movies_api.domain.model.Genre
import com.example.feature_movies_api.domain.LocalMoviesRepository
import com.example.feature_movies_api.domain.model.Movie
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPreferencesLocalMoviesRepository(private val sharedPreferences: SharedPreferences) : LocalMoviesRepository {
    private val gson = Gson()

    override suspend fun saveMovies(movies: List<Movie>) {
        with(sharedPreferences.edit()) {
            val jsonString = gson.toJson(movies)
            putString(KEY, jsonString)
            apply()
        }
    }

    override suspend fun getMovies(): List<Movie> {
        val moviesString = sharedPreferences.getString(KEY, null) ?: return emptyList()
        val type = object : TypeToken<List<Movie>>() {}.type
        return gson.fromJson(moviesString, type)
    }

    override suspend fun getMovies(genre: Genre): List<Movie> {
        val moviesString = sharedPreferences.getString(KEY, null) ?: return emptyList()
        val type = object : TypeToken<List<Movie>>() {}.type
        val moviesWithGenre = gson.fromJson<List<Movie>>(moviesString, type).filter { movie ->
            movie.genres.any { it == genre }
        }
        return moviesWithGenre
    }

    override suspend fun updateFavorite(movie: Movie) {
        val moviesString = sharedPreferences.getString(KEY, null) ?: return
        val type = object : TypeToken<List<Movie>>() {}.type
        val movies: MutableList<Movie> = gson.fromJson(moviesString, type)
        val movieIndex = movies.indexOfFirst { it.kinopoiskId == movie.kinopoiskId }

        with(sharedPreferences.edit()) {
            movies[movieIndex].isFavorite = !movies[movieIndex].isFavorite
            val jsonString = gson.toJson(movies)
            putString(KEY, jsonString)
            apply()
        }
    }

    companion object {
        private const val KEY = "local_movies_shared_preferences"
    }
}

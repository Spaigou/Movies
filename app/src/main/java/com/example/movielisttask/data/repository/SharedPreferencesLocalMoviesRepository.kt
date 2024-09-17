package com.example.movielisttask.data.repository

import android.content.SharedPreferences
import com.example.movielisttask.data.model.Genre
import com.example.movielisttask.domain.model.Movie
import com.example.movielisttask.domain.repository.LocalMoviesRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPreferencesLocalMoviesRepository(private val sharedPreferences: SharedPreferences) : LocalMoviesRepository {
    private val gson = Gson()

    override suspend fun saveMovies(movies: List<com.example.movielisttask.domain.model.Movie>) {
        with(sharedPreferences.edit()) {
            val jsonString = gson.toJson(movies)
            putString(KEY, jsonString)
            apply()
        }
    }

    override suspend fun getMovies(): List<com.example.movielisttask.domain.model.Movie> {
        val moviesString = sharedPreferences.getString(KEY, null) ?: return emptyList()
        val type = object : TypeToken<List<com.example.movielisttask.domain.model.Movie>>() {}.type
        return gson.fromJson(moviesString, type)
    }

    override suspend fun getMovies(genre: Genre): List<com.example.movielisttask.domain.model.Movie> {
        val moviesString = sharedPreferences.getString(KEY, null) ?: return emptyList()
        val type = object : TypeToken<List<com.example.movielisttask.domain.model.Movie>>() {}.type
        val moviesWithGenre = gson.fromJson<List<com.example.movielisttask.domain.model.Movie>>(moviesString, type).filter { movie ->
            movie.genres.any { it == genre }
        }
        return moviesWithGenre
    }

    companion object {
        private const val KEY = "local_movies_shared_preferences"
    }
}

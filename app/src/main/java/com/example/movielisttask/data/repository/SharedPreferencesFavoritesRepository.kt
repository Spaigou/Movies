package com.example.movielisttask.data.repository

import android.content.SharedPreferences
import com.example.movielisttask.data.model.Movie
import com.example.movielisttask.domain.repository.FavoritesRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPreferencesFavoritesRepository(private val sharedPreferences: SharedPreferences) : FavoritesRepository {
    private val gson = Gson()

    override suspend fun saveFavoriteMovies(movies: List<Movie>) {
        with(sharedPreferences.edit()) {
            val jsonString = gson.toJson(movies)
            putString(KEY, jsonString)
            apply()
        }
    }

    override suspend fun getFavoriteMovies(): List<Movie> {
        val moviesString = sharedPreferences.getString(KEY, null) ?: return emptyList()
        val type = object : TypeToken<List<Movie>>() {}.type
        return gson.fromJson(moviesString, type)
    }

    companion object {
        private const val KEY = "favorite_movies_shared_preferences"
    }
}

package com.example.movielisttask

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.movielisttask.data.repository.SharedPreferencesLocalMoviesRepository
import com.example.movielisttask.domain.repository.LocalMoviesRepository

class MoviesApplication : Application() {
    private lateinit var sharedPreferences: SharedPreferences
    val localMoviesRepository: LocalMoviesRepository by lazy {
        SharedPreferencesLocalMoviesRepository(sharedPreferences)
    }

    override fun onCreate() {
        super.onCreate()
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    companion object {
        const val SHARED_PREFERENCES_NAME = "MoviesApplication"
    }
}
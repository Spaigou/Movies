package com.example.movielisttask

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.movielisttask.data.model.room.MoviesDatabase
import com.example.movielisttask.data.repository.MoviesRepositoryImpl
import com.example.movielisttask.data.repository.RoomLocalMoviesRepository
import com.example.movielisttask.data.repository.SharedPreferencesLocalMoviesRepository
import com.example.movielisttask.domain.repository.LocalMoviesRepository
import com.example.movielisttask.domain.repository.MoviesRepository

class MoviesApplication : Application() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var db: MoviesDatabase

    private val moviesDao by lazy {
        db.moviesDao()
    }

    val moviesRepository: MoviesRepository by lazy {
        MoviesRepositoryImpl()
    }

//    val localMoviesRepository: LocalMoviesRepository by lazy {
//        SharedPreferencesLocalMoviesRepository(sharedPreferences)
//    }

    val localMoviesRepository: LocalMoviesRepository by lazy {
        RoomLocalMoviesRepository(moviesDao)
    }

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            MoviesDatabase::class.java,
            DATABASE_NAME
        ).build()
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    companion object {
        const val SHARED_PREFERENCES_NAME = "MoviesApplication"
        const val DATABASE_NAME = "movies_database"
    }
}
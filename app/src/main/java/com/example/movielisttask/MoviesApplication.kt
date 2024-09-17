package com.example.movielisttask

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.feature_movies_api.domain.LocalMoviesRepository
import com.example.feature_movies_database_impl.data.MoviesDatabase
import com.example.feature_remote_impl.domain.RemoteMoviesRepositoryImpl
import com.example.feature_movies_database_impl.domain.RoomLocalMoviesRepository
import com.example.feature_movies_prefs_impl.domain.SharedPreferencesLocalMoviesRepository
import com.example.feature_remote_api.domain.RemoteMoviesRepository

class MoviesApplication : Application() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var db: MoviesDatabase

    private val moviesDao by lazy {
        db.moviesDao()
    }

    val remoteMoviesRepository: RemoteMoviesRepository by lazy {
        RemoteMoviesRepositoryImpl()
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
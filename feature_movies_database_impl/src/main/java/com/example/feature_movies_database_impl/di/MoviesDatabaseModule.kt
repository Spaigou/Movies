package com.example.feature_movies_database_impl.di

import android.content.Context
import androidx.room.Room
import com.example.feature_movies_database_impl.data.MoviesDao
import com.example.feature_movies_database_impl.data.MoviesDatabase
import dagger.Module
import dagger.Provides

@Module
internal object MoviesDatabaseModule {
    private const val DATABASE_NAME = "movies_database"

    @Provides
    fun provideMoviesDao(database: MoviesDatabase): MoviesDao {
        return database.moviesDao()
    }

    @Provides
    fun provideMoviesDatabase(context: Context): MoviesDatabase {
        return Room.databaseBuilder(
            context,
            MoviesDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
}
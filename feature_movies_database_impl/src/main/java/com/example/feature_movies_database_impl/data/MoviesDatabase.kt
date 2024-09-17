package com.example.feature_movies_database_impl.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.feature_movies_database_impl.data.converters.CountryConverters
import com.example.feature_movies_database_impl.data.converters.GenreConverters

@Database(
    entities = [
        MovieEntity::class,
    ],
    version = 1,
)
@TypeConverters(CountryConverters::class, GenreConverters::class)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}
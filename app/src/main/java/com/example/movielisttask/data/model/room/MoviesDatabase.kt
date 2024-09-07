package com.example.movielisttask.data.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movielisttask.data.model.room.converters.CountryConverters
import com.example.movielisttask.data.model.room.converters.GenreConverters

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
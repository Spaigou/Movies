package com.example.movielisttask.data.model.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        MovieEntity::class,
        CountryEntity::class,
        GenreEntity::class,
    ],
    version = 1,
)
abstract class MoviesDatabase : RoomDatabase() {
}
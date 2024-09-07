package com.example.movielisttask.data.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface MoviesDao {
    @Query("SELECT * FROM movies ORDER BY timestamp_ms")
    suspend fun getMovies(): List<MovieEntity>

    @Query("DELETE FROM movies")
    suspend fun clearMovies()

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(entity: MovieEntity)
}
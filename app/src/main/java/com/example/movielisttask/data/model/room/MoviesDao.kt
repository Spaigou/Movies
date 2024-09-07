package com.example.movielisttask.data.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MoviesDao {
    @Query("SELECT * FROM Movies")
    suspend fun getMovies(): List<MovieEntity>

    @Query("DELETE FROM movies")
    suspend fun clearMovies()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: MovieEntity)
}
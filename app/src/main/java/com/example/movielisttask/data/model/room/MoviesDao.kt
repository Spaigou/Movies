package com.example.movielisttask.data.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movielisttask.data.model.Genre

@Dao
interface MoviesDao {
    @Query("SELECT * FROM movies ORDER BY timestamp_ms")
    suspend fun getMovies(): List<MovieEntity>

    @Query("SELECT * FROM movies WHERE genres LIKE ('%' || :genre || '%') ORDER BY timestamp_ms")
    suspend fun getMovies(genre: String): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(entity: MovieEntity)
}

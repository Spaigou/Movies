package com.example.movielisttask.data.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface MoviesDao {
    @Query("SELECT * FROM movies ORDER BY timestamp_ms")
    suspend fun getMovies(): List<MovieEntity>

    @Query("SELECT * FROM movies WHERE genres LIKE ('%' || :genre || '%') ORDER BY timestamp_ms")
    suspend fun getMovies(genre: String): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: MovieEntity)

    @Query("UPDATE movies SET is_favorite = :isFavorite WHERE kinopoisk_id = :id")
    suspend fun updateFavorite(id: Int, isFavorite: Boolean)
}

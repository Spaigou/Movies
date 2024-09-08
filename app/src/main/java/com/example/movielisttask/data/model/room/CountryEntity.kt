package com.example.movielisttask.data.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "countires")
data class CountryEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val genreId: Int,
    @ColumnInfo(name = "movie_id") val movieId: Int,
    @ColumnInfo(name = "country") val country: String
)
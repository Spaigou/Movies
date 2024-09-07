package com.example.movielisttask.data.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "countries",
)
data class CountryEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo("country") val id: Int,
    @ColumnInfo("country") val country: String,
)
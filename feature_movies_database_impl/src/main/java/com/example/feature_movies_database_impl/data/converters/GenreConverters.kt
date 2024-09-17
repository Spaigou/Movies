package com.example.feature_movies_database_impl.data.converters

import androidx.room.TypeConverter
import com.example.feature_movies_api.domain.model.Genre
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GenreConverters {
    @TypeConverter
    fun fromGenreList(genres: List<Genre>): String {
        return Gson().toJson(genres)
    }

    @TypeConverter
    fun toGenreList(data: String): List<Genre> {
        val listType = object : TypeToken<List<Genre>>() {}.type
        return Gson().fromJson(data, listType)
    }
}
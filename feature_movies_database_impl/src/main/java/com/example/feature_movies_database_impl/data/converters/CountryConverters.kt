package com.example.feature_movies_database_impl.data.converters

import androidx.room.TypeConverter
import com.example.feature_movies_api.domain.model.Country
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CountryConverters {
    @TypeConverter
    fun fromCountryList(countries: List<Country>): String {
        return Gson().toJson(countries)
    }

    @TypeConverter
    fun toCountryList(data: String): List<Country> {
        val listType = object : TypeToken<List<Country>>() {}.type
        return Gson().fromJson(data, listType)
    }
}
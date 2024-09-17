package com.example.feature_movies_database_impl.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.feature_movies_api.domain.model.Country
import com.example.feature_movies_api.domain.model.Genre

@Entity(
    tableName = "movies"
)
data class MovieEntity(
    @PrimaryKey @ColumnInfo("kinopoisk_id") val kinopoiskId: Int,
    @ColumnInfo("name_ru") val nameRu: String,
    @ColumnInfo("name_original") val nameOriginal: String?,
    @ColumnInfo("poster_url") val posterUrl: String,
    @ColumnInfo("cover_url") val coverUrl: String?,
    @ColumnInfo("logo_url") val logoUrl: String?,
    @ColumnInfo("rating_kinopoisk") val ratingKinopoisk: Double,
    @ColumnInfo("rating_imdb") val ratingImdb: Double,
    @ColumnInfo("web_url") val webUrl: String?,
    @ColumnInfo("year") val year: Int,
    @ColumnInfo("film_length") val filmLength: Int,
    @ColumnInfo("description") val description: String?,
    @ColumnInfo("short_description") val shortDescription: String?,
    @ColumnInfo("countries") val countries: List<Country>,
    @ColumnInfo("genres") val genres: List<Genre>,
    @ColumnInfo("is_favorite") val isFavorite: Boolean = false,
    @ColumnInfo(name = "timestamp_ms") val timestampMs: Long,
)
package com.example.movielisttask.domain.model

data class Movie(
    val kinopoiskId: Int,
    val nameRu: String,
    val nameOriginal: String?,
    val posterUrl: String,
    val coverUrl: String?,
    val logoUrl: String?,
    val ratingKinopoisk: Double,
    val ratingImdb: Double,
    val webUrl: String?,
    val year: Int,
    val filmLength: Int,
    val description: String,
    val shortDescription: String?,
    val countries: List<com.example.movielisttask.data.model.Country>,
    val genres: List<com.example.movielisttask.data.model.Genre>,
    var isFavorite: Boolean = false,
)

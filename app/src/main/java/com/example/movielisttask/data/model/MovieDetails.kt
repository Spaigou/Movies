package com.example.movielisttask.data.model

import com.example.movielisttask.data.model.Country
import com.example.movielisttask.data.model.Genre

data class MovieDetails(
    val kinopoiskId: Int,
    val nameRu: String,
    val nameOriginal: String,
    val posterUrl: String,
    val coverUrl: String,
    val logoUrl: String,
    val ratingKinopoisk: Double,
    val ratingImdb: Double,
    val webUrl: String,
    val year: Int,
    val filmLength: Int,
    val description: String,
    val shortDescription: String,
    val countries: List<Country>,
    val genres: List<Genre>,
)
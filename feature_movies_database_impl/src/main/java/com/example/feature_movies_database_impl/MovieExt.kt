package com.example.feature_movies_database_impl

import com.example.feature_movies_api.domain.model.Movie
import com.example.feature_movies_database_impl.data.MovieEntity

fun Movie.toMovieEntity() = MovieEntity(
    kinopoiskId = kinopoiskId,
    nameRu = nameRu,
    nameOriginal = nameOriginal,
    posterUrl = posterUrl,
    coverUrl = coverUrl,
    logoUrl = logoUrl,
    ratingKinopoisk = ratingKinopoisk,
    ratingImdb = ratingImdb,
    webUrl = webUrl,
    year = year,
    filmLength = filmLength,
    description = description,
    shortDescription = shortDescription,
    countries = countries,
    genres = genres,
    isFavorite = isFavorite,
    timestampMs = System.currentTimeMillis()
)

fun MovieEntity.toMovie() = Movie(
    kinopoiskId = kinopoiskId,
    nameRu = nameRu,
    nameOriginal = nameOriginal,
    posterUrl = posterUrl,
    coverUrl = coverUrl,
    logoUrl = logoUrl,
    ratingKinopoisk = ratingKinopoisk,
    ratingImdb = ratingImdb,
    webUrl = webUrl,
    year = year,
    filmLength = filmLength,
    description = description,
    shortDescription = shortDescription,
    countries = countries,
    genres = genres,
    isFavorite = isFavorite
)

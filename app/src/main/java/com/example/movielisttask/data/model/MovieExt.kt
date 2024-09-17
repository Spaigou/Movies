package com.example.movielisttask.data.model

import com.example.movielisttask.data.model.room.MovieEntity
import com.example.movielisttask.domain.model.Movie

fun Movie.onFavoriteClick() {
    isFavorite = !isFavorite
}

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

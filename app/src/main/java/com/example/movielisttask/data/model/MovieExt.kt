package com.example.movielisttask.data.model

import com.example.movielisttask.data.model.room.MovieEntity

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
    countries = countries.joinToString(",") { it.country },
    genres = genres.joinToString(",") { it.genre },
    isFavorite = isFavorite
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
    countries = countries.split(",").map { Country(it) },
    genres = genres.split(",").map { Genre(it) },
    isFavorite = isFavorite
)
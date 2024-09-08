package com.example.movielisttask.data.model.room

import androidx.room.Embedded
import androidx.room.Relation

data class MovieWithCountriesAndGenres(
    @Embedded val movie: MovieEntity,
    @Relation(
        parentColumn = "kinopoiskId",
        entityColumn = "movieId"
    )
    val countries: List<CountryEntity>,
    @Relation(
        parentColumn = "kinopoiskId",
        entityColumn = "movieId"
    )
    val genres: List<GenreEntity>
)

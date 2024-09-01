package com.example.movielisttask.data.model

data class Movie(
    val kinopoiskId: Int,
    val nameRu: String,
    val nameEn: String,
    val nameOriginal: String,
    val countries: List<Country>,
    val genres: List<Genre>,
    val ratingImbd: Double,
    val ratingKinopoisk: Double,
    val year: Int,
    val posterUrl: String,
    var isFavorite: Boolean = false,
    var onClick: () -> Unit = {},
) {
    fun onFavoriteClick() {
        isFavorite = !isFavorite
    }
}

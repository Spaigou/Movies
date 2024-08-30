package com.example.movielisttask.domain.model

data class Movie(
    val countries: List<Country>,
    val genres: List<Genre>,
    val kinopoiskId: Int,
    val nameEn: String,
    val nameOriginal: String,
    val nameRu: String,
    val posterUrl: String,
    val ratingImbd: Double,
    val ratingKinopoisk: Double,
    val type: String,
    val year: String,
    val description: String,
    val shortDescription: String,
    var isFavorite: Boolean = false,
    var onClick: () -> Unit,
) {
    fun onFavoriteClick() {
        isFavorite = !isFavorite
    }
}

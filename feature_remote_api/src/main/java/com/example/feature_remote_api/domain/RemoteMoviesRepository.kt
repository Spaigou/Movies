package com.example.feature_remote_api.domain

import com.example.feature_movies_api.domain.model.Movie


interface RemoteMoviesRepository {

    suspend fun getMoviesCollection(
        type: MoviesCollectionType = MoviesCollectionType.TOP_POPULAR_MOVIES,
        page: Int = 1,
    ): List<Movie>?
}
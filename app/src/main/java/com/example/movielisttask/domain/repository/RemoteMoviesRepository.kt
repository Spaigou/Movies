package com.example.movielisttask.domain.repository

import com.example.feature_movies_api.domain.model.Movie
import com.example.movielisttask.common.MoviesCollectionType

interface RemoteMoviesRepository {

    suspend fun getMoviesCollection(
        type: MoviesCollectionType = MoviesCollectionType.TOP_POPULAR_MOVIES,
        page: Int = 1,
    ): List<Movie>?

}
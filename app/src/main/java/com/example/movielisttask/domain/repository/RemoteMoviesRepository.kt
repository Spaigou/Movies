package com.example.movielisttask.domain.repository

import com.example.movielisttask.data.model.Filters
import com.example.movielisttask.data.model.Movie

interface RemoteMoviesRepository {
    suspend fun getMoviesCollection(type: String, page: Int): List<Movie>?

    suspend fun getMovieById(movieId: Int): Movie?

    suspend fun getFilters(): Filters?
}
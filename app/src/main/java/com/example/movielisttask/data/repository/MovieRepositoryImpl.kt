package com.example.movielisttask.data.repository

import com.example.movielisttask.data.api.RetrofitInstance
import com.example.movielisttask.data.model.Movie
import com.example.movielisttask.data.model.MovieDetails
import com.example.movielisttask.domain.repository.MovieRepository

class MovieRepositoryImpl : MovieRepository {

    override suspend fun getMoviesCollection(type: String, page: Int): List<Movie>? {
        return RetrofitInstance.api.getTopMovies(type, page).body()?.items
    }

    override suspend fun getMovieById(movieId: Int): MovieDetails? {
        return RetrofitInstance.api.getMovieById(movieId).body()
    }
}
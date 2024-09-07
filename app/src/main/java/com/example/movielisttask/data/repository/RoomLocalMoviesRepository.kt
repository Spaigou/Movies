package com.example.movielisttask.data.repository

import com.example.movielisttask.data.model.Movie
import com.example.movielisttask.data.model.room.MoviesDao
import com.example.movielisttask.data.model.toMovie
import com.example.movielisttask.data.model.toMovieEntity
import com.example.movielisttask.domain.repository.LocalMoviesRepository

class RoomLocalMoviesRepository(private val moviesDao: MoviesDao) : LocalMoviesRepository {
    override suspend fun saveMovies(movies: List<Movie>) {
        movies.forEach { movie ->
            moviesDao.insert(movie.toMovieEntity())
        }
    }

    override suspend fun getMovies(): List<Movie> {
        return moviesDao.getMovies().map { it.toMovie() }
    }
}
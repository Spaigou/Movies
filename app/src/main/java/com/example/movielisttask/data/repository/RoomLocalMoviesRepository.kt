package com.example.movielisttask.data.repository

import com.example.movielisttask.domain.model.Movie
import com.example.movielisttask.data.model.room.MoviesDao
import com.example.movielisttask.data.model.toMovie
import com.example.movielisttask.data.model.toMovieEntity
import com.example.movielisttask.domain.model.Genre
import com.example.movielisttask.domain.repository.LocalMoviesRepository

class RoomLocalMoviesRepository(private val moviesDao: MoviesDao) : LocalMoviesRepository {
    override suspend fun saveMovies(movies: List<Movie>) {
        movies.map { movie ->
            moviesDao.insert(movie.toMovieEntity())
        }
    }

    override suspend fun getMovies(): List<Movie> {
        return moviesDao.getMovies().map { movieEntity ->
            movieEntity.toMovie()
        }
    }

    override suspend fun getMovies(genre: Genre): List<Movie> {
        return moviesDao.getMovies(genre.genre).map { movieEntity ->
            movieEntity.toMovie()
        }
    }

    override suspend fun updateFavorite(movie: Movie) {
        moviesDao.updateFavorite(movie.kinopoiskId, !movie.isFavorite)
    }
}

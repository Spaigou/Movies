package com.example.feature_movies_database_impl.domain

import com.example.feature_movies_api.domain.model.Genre
import com.example.feature_movies_api.domain.LocalMoviesRepository
import com.example.feature_movies_api.domain.model.Movie
import com.example.feature_movies_database_impl.data.MoviesDao
import com.example.feature_movies_database_impl.toMovie
import com.example.feature_movies_database_impl.toMovieEntity

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

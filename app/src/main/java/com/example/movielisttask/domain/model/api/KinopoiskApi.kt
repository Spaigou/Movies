package com.example.movielisttask.domain.model.api

import com.example.movielisttask.domain.model.Movie
import com.example.movielisttask.domain.model.TopMovies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface KinopoiskApi {
    @GET("collections")
    @Headers("X-API-KEY: 66cb4a47-61c0-4360-a6a6-001384f138ba", "Content-Type: application/json")
    suspend fun getTopMovies(@Query("type") type: String, @Query("page") page: Int): Response<TopMovies>

    @GET("{id}")
    @Headers("X-API-KEY: 66cb4a47-61c0-4360-a6a6-001384f138ba", "Content-Type: application/json")
    suspend fun getMovieById(@Path("id") filmId: Int): Response<Movie>
}
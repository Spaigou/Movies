package com.example.feature_movies_api.di

import com.example.feature_movies_api.domain.LocalMoviesRepository

interface LocalMoviesApi {
    val localMoviesRepository: LocalMoviesRepository
}

package com.example.feature_movies_database_impl.di

import com.example.feature_movies_api.domain.LocalMoviesRepository
import com.example.feature_movies_database_impl.domain.RoomLocalMoviesRepository
import dagger.Binds
import dagger.Module

@Module
internal interface LocalMoviesRepositoryModule {

    @Binds
    fun bindLocalMoviesRepository(roomLocalMoviesRepository: RoomLocalMoviesRepository): LocalMoviesRepository
}
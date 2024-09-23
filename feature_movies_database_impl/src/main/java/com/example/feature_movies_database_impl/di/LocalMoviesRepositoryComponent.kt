package com.example.feature_movies_database_impl.di

import com.example.feature_movies_api.di.LocalMoviesApi
import dagger.Component

@Component(
    modules = [
        MoviesDatabaseModule::class,
        LocalMoviesRepositoryModule::class,
    ]
)
internal interface LocalMoviesRepositoryComponent {
    companion object {
//        fun create(): LocalMoviesApi = Dagg
    }
}

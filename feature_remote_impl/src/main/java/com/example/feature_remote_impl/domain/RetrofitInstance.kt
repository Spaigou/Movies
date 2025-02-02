package com.example.feature_remote_impl.domain

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/films/"

    val api: com.example.feature_remote_api.domain.KinopoiskApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(com.example.feature_remote_api.domain.KinopoiskApi::class.java)
    }
}

package com.example.feature_remote_impl.domain

import android.util.Log
import com.example.feature_movies_api.domain.model.Movie
import com.example.feature_remote_api.domain.MoviesCollectionType
import com.example.feature_remote_api.domain.RemoteMoviesRepository
import retrofit2.HttpException
import java.io.IOException

class RemoteMoviesRepositoryImpl : RemoteMoviesRepository {

    override suspend fun getMoviesCollection(type: MoviesCollectionType, page: Int): List<Movie>? {
        val response = try {
            RetrofitInstance.api.getTopMovies(type.name, page)
        } catch (e: HttpException) {
            Log.d(TAG, "HttpException occurred")
            return null
        } catch (e: IOException) {
            Log.d(TAG, "IOException occurred")
            return null
        }
        return if (response.isSuccessful && response.body() != null)
            response.body()!!.items
        else
            null
    }

    companion object {
        private const val TAG = "MovieRepositoryImpl"
    }
}

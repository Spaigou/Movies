package com.example.movielisttask.domain.model

import android.util.Log
import coil.network.HttpException
import com.example.movielisttask.domain.model.api.RetrofitInstance
import java.io.IOException

class MoviesRepository {
    suspend fun getTopMovies(type: String = MoviesCollectionType.TOP_POPULAR_ALL.name, page: Int = 1): List<Movie>? {
        val response = try {
            RetrofitInstance.api.getTopMovies(type, page)
        } catch (e: IOException) {
            Log.d(TAG, "IOException occurred")
            return null
        } catch (e: HttpException) {
            Log.d(TAG, "HttpException occurred")
            return null
        }
        return if (response.isSuccessful && response.body() != null) {
            response.body()!!.items
        } else {
            Log.d(TAG, "Response was not successful")
            null
        }
    }

    suspend fun getMovie(id: Int): Movie? {
        val response = try {
            RetrofitInstance.api.getMovieById(id)
        } catch (e: IOException) {
            Log.d(TAG, "IOException occurred")
            return null
        } catch (e: HttpException) {
            Log.d(TAG, "HttpException occurred")
            return null
        }
        return if (response.isSuccessful && response.body() != null) {
            response.body()!!
        } else {
            Log.d(TAG, "Response was not successful")
            null
        }
    }

    companion object {
        private const val TAG = "MoviesRepository"
    }
}
package com.example.movielisttask.data.repository

import android.util.Log
import com.example.movielisttask.data.model.Filters
import com.example.movielisttask.data.remote.RetrofitInstance
import com.example.movielisttask.data.model.Movie
import com.example.movielisttask.domain.repository.RemoteMoviesRepository
import retrofit2.HttpException
import java.io.IOException

class RemoteMoviesRepositoryImpl : RemoteMoviesRepository {

    override suspend fun getMoviesCollection(type: String, page: Int): List<Movie>? {
        val response = try {
            RetrofitInstance.api.getTopMovies(type, page)
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

    override suspend fun getMovieById(movieId: Int): Movie? {
        val response = try {
            RetrofitInstance.api.getMovieById(movieId)
        } catch (e: HttpException) {
            Log.d(TAG, "HttpException occurred")
            return null
        } catch (e: IOException) {
            Log.d(TAG, "IOException occurred")
            return null
        }
        return if (response.isSuccessful && response.body() != null)
            response.body()!!
        else
            null
    }

    override suspend fun getFilters(): Filters? {
        val response = try {
            RetrofitInstance.api.getFilters()
        } catch (e: HttpException) {
            Log.d(TAG, "HttpException occurred")
            return null
        } catch (e: IOException) {
            Log.d(TAG, "IOException occurred")
            return null
        }
        return if (response.isSuccessful && response.body() != null)
            response.body()!!
        else
            null
    }

    companion object {
        private const val TAG = "MovieRepositoryImpl"
    }
}
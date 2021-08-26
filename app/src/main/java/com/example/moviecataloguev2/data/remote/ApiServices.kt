package com.example.moviecataloguev2.data.remote

import com.example.moviecataloguev2.BuildConfig.API_KEY
import com.example.moviecataloguev2.data.remote.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    @GET("movie/popular")
    suspend fun getPopular(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<MovieResponse>
}
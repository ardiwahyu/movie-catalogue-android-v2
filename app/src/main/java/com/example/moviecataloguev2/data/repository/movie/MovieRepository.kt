package com.example.moviecataloguev2.data.repository.movie

import androidx.lifecycle.MutableLiveData
import com.example.moviecataloguev2.data.remote.ApiServices
import com.example.moviecataloguev2.data.remote.model.Error
import com.example.moviecataloguev2.data.remote.model.Movie
import com.example.moviecataloguev2.data.remote.model.MovieResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val apiServices: ApiServices
) {
    private val loading = MutableLiveData<Boolean>()
    private val error = MutableLiveData<String>()
    private val listMovie = MutableLiveData<MovieResponse>()

    suspend fun getPopular(language: String, page: Int): MovieResult {
        return withContext(Dispatchers.IO) {
            loading.postValue(true)
            try {
                val response = apiServices.getPopular(
                    language = language,
                    page = page
                )
                if (response.isSuccessful) {
                    listMovie.postValue(response.body())
                } else {
                    val errorResponse = Gson().fromJson(response.errorBody()?.string(), Error::class.java)
                    error.postValue(errorResponse.statusMessage)
                }
            } catch (e: Exception) {
                error.postValue(e.localizedMessage)
            }
            loading.postValue(false)
            return@withContext MovieResult(loading, error, listMovie)
        }
    }
}
package com.example.moviecataloguev2.data.repository.movie

import androidx.lifecycle.MutableLiveData
import com.example.moviecataloguev2.data.local.db.dao.GenreDao
import com.example.moviecataloguev2.data.remote.ApiServices
import com.example.moviecataloguev2.data.remote.model.Error
import com.example.moviecataloguev2.data.remote.model.Movie
import com.example.moviecataloguev2.data.remote.model.MovieResponse
import com.example.moviecataloguev2.data.repository.genre.GenreRepository
import com.google.gson.Gson
import kotlinx.coroutines.*
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val apiServices: ApiServices,
    private val genreRepository: GenreRepository,
    private val genreDao: GenreDao
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
                    val responseBody = response.body()
                    val listMovieNew = arrayListOf<Movie>()
                    responseBody?.result?.forEach { movie ->
                        movie.listGenreString = arrayListOf()
                        movie.listGenreString.addAll(genreDao.getNameGenre(movie.genreId))
                        listMovieNew.add(movie)
                    }
                    listMovie.postValue(
                        MovieResponse(
                            page = responseBody!!.page,
                            result = listMovieNew,
                            totalResults = responseBody.totalResults,
                            totalPages = responseBody.totalPages
                    ))
                } else {
                    val errorResponse = Gson().fromJson(response.errorBody()?.string(), Error::class.java)
                    error.postValue(errorResponse.statusMessage)
                }
            } catch (e: Exception) {
                error.postValue(e.localizedMessage)
                Timber.d(e.localizedMessage)
            }
            loading.postValue(false)
            return@withContext MovieResult(loading, error, listMovie)
        }
    }
}
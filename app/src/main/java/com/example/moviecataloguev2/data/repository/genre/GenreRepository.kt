package com.example.moviecataloguev2.data.repository.genre

import androidx.lifecycle.MutableLiveData
import com.example.moviecataloguev2.data.local.db.dao.GenreDao
import com.example.moviecataloguev2.data.local.db.entities.GenreVo
import com.example.moviecataloguev2.data.remote.ApiServices
import com.example.moviecataloguev2.data.remote.model.Error
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class GenreRepository @Inject constructor(
    private val apiServices: ApiServices,
    private val genreDao: GenreDao
) {
    private val loading = MutableLiveData<Boolean>()
    private val error = MutableLiveData<String>()
    private val listGenre = MutableLiveData<List<String>>()

    suspend fun loadGenre(language: String): GenreResult {
        return withContext(Dispatchers.IO) {
            try {
                loading.postValue(true)
                val response = apiServices.getGenre(language = language)
                if (response.isSuccessful) {
                    val genreVo = arrayListOf<GenreVo>()
                    response.body()?.genres?.forEach {
                        genreVo.add(
                            GenreVo(
                                id = it.id, name = it.name
                            ))
                    }
                    genreDao.insert(genreVo)
                } else {
                    val errorResponse = Gson().fromJson(response.errorBody()?.string(), Error::class.java)
                    error.postValue(errorResponse.statusMessage)
                }
            } catch (e: Exception) {
                error.postValue(e.localizedMessage)
            }
            loading.postValue(false)
            return@withContext GenreResult(loading, error, listGenre)
        }
    }

//    suspend fun getGenre(listId: List<Int>): List<String> {
//        return withContext(Dispatchers.IO) {
//            return@withContext genreDao.getNameGenre(listId)
//        }
//    }

    suspend fun getAllGenre(): CekGenreResult {
        val status = MutableLiveData<Boolean>()
        return withContext(Dispatchers.IO) {
            loading.postValue(true)
            status.postValue(genreDao.getAllGenre().isNotEmpty())
            loading.postValue(false)
            return@withContext CekGenreResult(loading, status)
        }
    }
}
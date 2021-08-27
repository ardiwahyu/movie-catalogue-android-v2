package com.example.moviecataloguev2.ui.splashscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecataloguev2.data.repository.genre.CekGenreResult
import com.example.moviecataloguev2.data.repository.genre.GenreRepository
import com.example.moviecataloguev2.data.repository.genre.GenreResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val genreRepository: GenreRepository
): ViewModel() {
    private val cekGenreResult = MutableLiveData<CekGenreResult>()
    val loadingCek = Transformations.switchMap(cekGenreResult) { it.loading }
    val status = Transformations.switchMap(cekGenreResult) { it.status }
    fun cekGenre() {
        viewModelScope.launch {
            cekGenreResult.postValue(genreRepository.getAllGenre())
        }
    }

    private val genreResult = MutableLiveData<GenreResult>()
    val loading = Transformations.switchMap(genreResult) { it.loading }
    val error = Transformations.switchMap(genreResult) { it.error }
    fun loadGenre(language: String) {
        viewModelScope.launch {
            genreResult.postValue(genreRepository.loadGenre(language))
        }
    }
}
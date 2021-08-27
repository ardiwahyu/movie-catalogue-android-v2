package com.example.moviecataloguev2.ui.main.movie.popular

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecataloguev2.data.repository.movie.MovieRepository
import com.example.moviecataloguev2.data.repository.movie.MovieResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val movieRepository: MovieRepository
): ViewModel() {

    private val movieResult = MutableLiveData<MovieResult>()
    val loading = Transformations.switchMap(movieResult) { it.loading }
    val error = Transformations.switchMap(movieResult) { it.error }
    val results = Transformations.switchMap(movieResult) { it.listMovie }

    fun getPopular(language: String, page: Int) {
        viewModelScope.launch {
            movieResult.postValue(movieRepository.getPopular(language, page))
        }
    }
}
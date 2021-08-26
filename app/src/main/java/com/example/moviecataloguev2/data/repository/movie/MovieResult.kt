package com.example.moviecataloguev2.data.repository.movie

import androidx.lifecycle.LiveData
import com.example.moviecataloguev2.data.remote.model.MovieResponse

class MovieResult (
    val loading: LiveData<Boolean>,
    val error: LiveData<String>,
    val listMovie: LiveData<MovieResponse>
)
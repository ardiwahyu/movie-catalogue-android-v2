package com.example.moviecataloguev2.data.repository.genre

import androidx.lifecycle.LiveData

class GenreResult (
    val loading: LiveData<Boolean>,
    val error: LiveData<String>,
    val genre: LiveData<List<String>>
)
package com.example.moviecataloguev2.data.repository.genre

import androidx.lifecycle.LiveData

class CekGenreResult (
    val loading: LiveData<Boolean>,
    val status: LiveData<Boolean>
)
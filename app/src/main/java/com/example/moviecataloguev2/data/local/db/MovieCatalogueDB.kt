package com.example.moviecataloguev2.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviecataloguev2.data.local.db.dao.GenreDao
import com.example.moviecataloguev2.data.local.db.entities.GenreVo

@Database(entities = [
    GenreVo::class
], version = 1, exportSchema = false)
abstract class MovieCatalogueDB: RoomDatabase() {
    abstract fun genreDao(): GenreDao
}
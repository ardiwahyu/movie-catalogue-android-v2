package com.example.moviecataloguev2.data.local.db.dao

import androidx.room.*
import com.example.moviecataloguev2.data.local.db.entities.GenreVo

@Dao
interface GenreDao {
    @Query("SELECT name FROM genre WHERE id IN (:listId)")
    suspend fun getNameGenre(listId: List<Int>): List<String>

    @Query("SELECT * FROM genre")
    suspend fun getAllGenre(): List<GenreVo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(listGenre: List<GenreVo>)

    @Query("DELETE FROM genre")
    suspend fun delete()
}
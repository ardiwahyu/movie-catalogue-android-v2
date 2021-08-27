package com.example.moviecataloguev2.data.local.db.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "genre")
class GenreVo (
    @PrimaryKey(autoGenerate = false) val id: Int,
    val name: String
): Parcelable
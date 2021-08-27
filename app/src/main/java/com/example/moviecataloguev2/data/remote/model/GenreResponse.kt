package com.example.moviecataloguev2.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class GenreResponse (
    @SerializedName("genres") val genres: List<Genre>
): Parcelable
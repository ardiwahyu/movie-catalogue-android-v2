package com.example.moviecataloguev2.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Genre (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
): Parcelable
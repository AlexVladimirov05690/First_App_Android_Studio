package com.example.findfilms

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Film(
    val id: Int,
    val title: String,
    val poster: Int,
    val description: Int,
    val short_desc: Int,
    var isInFavorites: Boolean = false
) : Parcelable
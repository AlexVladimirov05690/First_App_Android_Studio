package com.example.findfilms

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Film (
    val title: String,
    val poster: Int,
    val description: Int
        ): Parcelable
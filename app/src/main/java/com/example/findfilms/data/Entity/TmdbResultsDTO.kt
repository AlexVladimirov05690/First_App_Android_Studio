package com.example.findfilms.com.example.findfilms.data.Entity

import com.google.gson.annotations.SerializedName

data class TmdbResultsDTO (
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val tmbFilms: List<TmdbFilm>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
        )
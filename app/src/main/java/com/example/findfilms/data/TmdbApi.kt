package com.example.findfilms.com.example.findfilms.data


import com.example.findfilms.com.example.findfilms.data.Entity.TmdbResultsDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi {
    @GET("3/movie/popular")
    fun getFilms(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<TmdbResultsDTO>
}
package com.example.findfilms.domain

import com.example.findfilms.API
import com.example.findfilms.com.example.findfilms.data.Entity.TmdbResultsDTO
import com.example.findfilms.com.example.findfilms.data.TmdbApi
import com.example.findfilms.com.example.findfilms.utils.Converter
import com.example.findfilms.data.MainRepository
import com.example.findfilms.viewmodel.HomeFragmentViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Interactor(val repo: MainRepository, private val retrofitService: TmdbApi) {
    fun getFilmsFromApi(page: Int, callback: HomeFragmentViewModel.ApiCallback) {
        retrofitService.getFilms(API.KEY, "ru-RU", page).enqueue(object : Callback<TmdbResultsDTO> {
            override fun onResponse(
                call: Call<TmdbResultsDTO>,
                response: Response<TmdbResultsDTO>
            ) {
                callback.onSuccess(Converter.convertApiListToDtoList(response.body()?.tmbFilms))
            }

            override fun onFailure(call: Call<TmdbResultsDTO>, t: Throwable) {
                callback.onFailure()
            }

        })
    }
}
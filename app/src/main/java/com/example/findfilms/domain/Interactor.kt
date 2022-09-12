package com.example.findfilms.domain

import androidx.lifecycle.LiveData
import com.example.findfilms.API
import com.example.findfilms.com.example.findfilms.data.Entity.TmdbResultsDTO
import com.example.findfilms.com.example.findfilms.data.TmdbApi
import com.example.findfilms.com.example.findfilms.utils.Converter
import com.example.findfilms.data.Entity.Film
import com.example.findfilms.data.MainRepository
import com.example.findfilms.data.PreferenceProvider
import com.example.findfilms.viewmodel.HomeFragmentViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Interactor(val repo: MainRepository, private val retrofitService: TmdbApi, private val preference: PreferenceProvider) {
    var list = mutableListOf<Film>()
    fun getFilmsFromApi(page: Int, callback: HomeFragmentViewModel.ApiCallback) {
        retrofitService.getFilms(getDefaultCategoryToPreference(), API.KEY, "ru-RU", page).enqueue(object : Callback<TmdbResultsDTO> {
            override fun onResponse(
                call: Call<TmdbResultsDTO>,
                response: Response<TmdbResultsDTO>
            ) {
                list = Converter.convertApiListToDtoList(response.body()?.tmbFilms).toMutableList()
                list.forEach{
                    repo.putDb(list)
                }
                callback.onSuccess()
            }

            override fun onFailure(call: Call<TmdbResultsDTO>, t: Throwable) {
                callback.onFailure()
            }

        })
    }

    fun saveDefaultCategoryToPreference(category: String) {
        preference.saveDefaultCategory(category)
    }

    fun getDefaultCategoryToPreference() = preference.getDefaultCategory()

    fun getFilmsFromDB(): LiveData<List<Film>> = repo.getAllFromDb()

    fun clearFilmsFromDB(list: List<Film>) = repo.clearAllFromDb(list)




}
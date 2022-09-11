package com.example.findfilms.domain

import com.example.findfilms.API
import com.example.findfilms.com.example.findfilms.data.Entity.TmdbResultsDTO
import com.example.findfilms.com.example.findfilms.data.TmdbApi
import com.example.findfilms.com.example.findfilms.utils.Converter
import com.example.findfilms.data.Entity.Film
import com.example.findfilms.data.MainRepository
import com.example.findfilms.data.PreferenceProvider
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Interactor(val repo: MainRepository, private val retrofitService: TmdbApi, private val preference: PreferenceProvider) {
    var progressBarState: BehaviorSubject<Boolean> = BehaviorSubject.create()
    fun getFilmsFromApi(page: Int) {
        progressBarState.onNext(true)
        retrofitService.getFilms(getDefaultCategoryToPreference(), API.KEY, "ru-RU", page).enqueue(object : Callback<TmdbResultsDTO> {
            override fun onResponse(
                call: Call<TmdbResultsDTO>,
                response: Response<TmdbResultsDTO>
            ) {
                val list = Converter.convertApiListToDtoList(response.body()?.tmbFilms)
                Completable.fromSingle<List<Film>> {
                    repo.putDb(list)
                }
                    .subscribeOn(Schedulers.io())
                    .subscribe()
                progressBarState.onNext(false)
            }

            override fun onFailure(call: Call<TmdbResultsDTO>, t: Throwable) {
                progressBarState.onNext(false)
            }

        })
    }

    fun saveDefaultCategoryToPreference(category: String) {
        preference.saveDefaultCategory(category)
    }

    fun getDefaultCategoryToPreference() = preference.getDefaultCategory()

    fun getFilmsFromDB(): Observable<List<Film>> = repo.getAllFromDb()

    fun clearFilmsFromDB(list: List<Film>) = repo.clearAllFromDb(list)




}
package com.example.findfilms.domain

import com.example.findfilms.API
import com.example.findfilms.com.example.findfilms.data.Entity.TmdbResultsDTO
import com.example.findfilms.com.example.findfilms.data.TmdbApi
import com.example.findfilms.data.Entity.FavoritesFilm
import com.example.findfilms.data.Entity.Film
import com.example.findfilms.data.repositories.MainRepository
import com.example.findfilms.data.PreferenceProvider
import com.example.findfilms.data.repositories.FavoriteRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Interactor(
    val repo: MainRepository,
    val favoriteRepo: FavoriteRepository,
    private val retrofitService: TmdbApi,
    private val preference: PreferenceProvider,
) {
    var progressBarState: BehaviorSubject<Boolean> = BehaviorSubject.create()
    fun getFilmsFromApi(page: Int) {
        progressBarState.onNext(true)
        retrofitService.getFilms(getDefaultCategoryToPreference(), API.KEY, "ru-RU", page)
            .enqueue(object : Callback<TmdbResultsDTO> {
                override fun onResponse(
                    call: Call<TmdbResultsDTO>,
                    response: Response<TmdbResultsDTO>,
                ) {
                    //val list = Converter.convertApiListToDtoList(response.body()?.tmbFilms)
                    val list = mutableListOf<Film>()
                    val requestAPI = response.body()?.tmbFilms
                    Observable.fromArray(requestAPI)
                        .subscribeOn(Schedulers.io())
                        .subscribe { requestAPI ->
                            requestAPI?.forEach {
                                list.add(
                                    Film(
                                        title = it.title,
                                        poster = it.posterPath,
                                        description = it.overview,
                                        rating = it.voteAverage,
                                        isInFavorites = false
                                    )
                                )
                            }
                        }
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

    fun checkPromoPeriod(): Boolean {
        return preference.checkTrialPeriod()
    }

    fun getDefaultCategoryToPreference() = preference.getDefaultCategory()

    fun getFilmsFromDB(): Observable<List<Film>> = repo.getAllFromDb()

    fun getFavoritesFilms(): Observable<List<Film>> {
        val list = favoriteRepo.getAllFavoritesFilms()
        var listFilm = mutableListOf<Film>()
        list.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { favoritesFilms ->
                favoritesFilms.forEach {
                    listFilm.add(favoriteFilmToFilm(it))
                }
            }
        return Observable.fromArray(listFilm)
    }


    fun addFavoriteFilm(film: Film) {
        favoriteRepo.putToDb(filmToFavoriteFilm(film))
    }

    fun deleteFavoriteFilm(film: Film) {
        favoriteRepo.deleteFromDb(filmToFavoriteFilm(film))
    }

    private fun filmToFavoriteFilm(film: Film): FavoritesFilm {
        return FavoritesFilm(
            id = film.id,
            title = film.title,
            poster = film.poster,
            description = film.description,
            rating = film.rating,
            isInFavorites = true
        )
    }

    private fun favoriteFilmToFilm(favoritesFilm: FavoritesFilm): Film {
        return Film(
            id = favoritesFilm.id,
            title = favoritesFilm.title,
            poster = favoritesFilm.poster,
            description = favoritesFilm.description,
            rating = favoritesFilm.rating,
            isInFavorites = true
        )
    }


}
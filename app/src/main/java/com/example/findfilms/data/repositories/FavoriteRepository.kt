package com.example.findfilms.data.repositories

import com.example.findfilms.data.Entity.FavoritesFilm
import com.example.findfilms.data.dao.FavoritesFilmDao
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.Executors

class FavoriteRepository(private val favoritesFilmDao: FavoritesFilmDao) {

    fun putToDb(favoritesFilm: FavoritesFilm) {
        Executors.newSingleThreadExecutor().execute {
            favoritesFilmDao.insertFilm(favoritesFilm)
        }
    }
    fun deleteFromDb(favoritesFilm: FavoritesFilm) {
        Executors.newSingleThreadExecutor().execute{
            favoritesFilmDao.deleteFilm(favoritesFilm)
        }
    }
    fun getAllFavoritesFilms(): Observable<List<FavoritesFilm>> = favoritesFilmDao.getFavoritesFilms()
}
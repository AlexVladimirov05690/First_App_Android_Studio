package com.example.findfilms.data.repositories

import com.example.findfilms.data.Entity.Film
import com.example.findfilms.data.dao.FilmDao
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.Executors


class MainRepository(private val filmDao: FilmDao) {

    fun putDb(films: List<Film>) {
        Executors.newSingleThreadExecutor().execute {
            filmDao.insertAll(films)
        }
    }

    fun getAllFromDb(): Observable<List<Film>> = filmDao.getCachedFilms()

}
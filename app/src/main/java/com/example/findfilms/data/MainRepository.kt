package com.example.findfilms.data


import com.example.findfilms.data.Entity.Film
import com.example.findfilms.data.dao.FilmDao
import java.util.concurrent.Executors


class MainRepository(private val filmDao: FilmDao) {

    fun putDb(films: List<Film>) {
        Executors.newSingleThreadExecutor().execute {
            filmDao.insertAll(films)
        }
    }
    fun getAllFromDb(): List<Film> {
        return filmDao.getCachedFilms()
    }
    fun clearAllFromDb(films: List<Film>) {
        filmDao.clearCachedFilms(films)
    }

}
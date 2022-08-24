package com.example.findfilms.data


import androidx.lifecycle.LiveData
import com.example.findfilms.data.Entity.Film
import com.example.findfilms.data.dao.FilmDao
import java.util.concurrent.Executors


class MainRepository(private val filmDao: FilmDao) {

    fun putDb(films: List<Film>) {
        Executors.newSingleThreadExecutor().execute {
            filmDao.insertAll(films)
        }
    }
    fun getAllFromDb(): LiveData<List<Film>> = filmDao.getCachedFilms()

    fun clearAllFromDb(films: List<Film>) {
        filmDao.clearCachedFilms(films)
    }

}
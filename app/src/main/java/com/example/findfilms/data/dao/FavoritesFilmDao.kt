package com.example.findfilms.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.findfilms.data.Entity.FavoritesFilm
import io.reactivex.rxjava3.core.Observable

@Dao
interface FavoritesFilmDao {
    @Query("SELECT * FROM favorites_films")
    fun getFavoritesFilms(): Observable<List<FavoritesFilm>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFilm(favoritesFilm: FavoritesFilm)

    @Delete
    fun deleteFilm(favoritesFilm: FavoritesFilm)
}
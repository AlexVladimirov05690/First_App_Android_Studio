package com.example.findfilms.data.dao

import androidx.room.*
import com.example.findfilms.data.Entity.Film

@Dao
interface FilmDao {
    @Query("SELECT * FROM cached_films")
    fun getCachedFilms(): List<Film>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Film>)

    @Delete
    fun clearCachedFilms(list: List<Film>)
}
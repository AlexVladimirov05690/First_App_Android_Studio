package com.example.findfilms.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.findfilms.data.Entity.FavoritesFilm
import com.example.findfilms.data.dao.FavoritesFilmDao

@Database(entities = [FavoritesFilm::class], version = 1, exportSchema = false)
abstract class FavoriteFilmsDatabase: RoomDatabase() {
    abstract fun favoritesFilmsDao(): FavoritesFilmDao
}
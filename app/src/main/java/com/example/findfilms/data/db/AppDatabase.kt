package com.example.findfilms.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.findfilms.data.entity.Film
import com.example.findfilms.data.dao.FilmDao

@Database(entities = [Film::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun filmDao(): FilmDao
}
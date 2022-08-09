package com.example.findfilms.data

import android.content.ContentValues
import android.database.Cursor
import com.example.findfilms.data.db.DatabaseHelper
import com.example.findfilms.domain.Film


class MainRepository(dataBaseHelper: DatabaseHelper) {
    private val sqlDb = dataBaseHelper.readableDatabase
    private lateinit var cursorFavorite: Cursor
    private lateinit var cursor: Cursor

    fun putDb(film: Film) {
        val cv = ContentValues()
        cv.apply {
            put(DatabaseHelper.COLUMN_TITLE, film.title)
            put(DatabaseHelper.COLUMN_POSTER, film.poster)
            put(DatabaseHelper.COLUMN_DESCRIPTION, film.description)
            put(DatabaseHelper.COLUMN_RATING, film.rating)
            put(DatabaseHelper.COLUMN_FAVORITES, booleanToInt(film.isInFavorites))
        }
        sqlDb.insert(DatabaseHelper.TABLE_NAME, null, cv)

    }

    fun getAllFromDb(): List<Film> {
        sqlDb.rawQuery("SELECT * FROM ${DatabaseHelper.TABLE_NAME}", null).also { cursor = it }
        val result = mutableListOf<Film>()
        if (cursor.moveToFirst()) {
            do {
                val title = cursor.getString(1)
                val poster = cursor.getString(2)
                val description = cursor.getString(3)
                val rating = cursor.getDouble(4)
                val isFavorites = cursor.getInt(5)
                result.add(Film(title, poster, description, rating, IntToBoolean(isFavorites)))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return result
    }

    fun getFavoritesFromDb() : List<Film> {
        sqlDb.rawQuery("SELECT * FROM ${DatabaseHelper.TABLE_NAME} WHERE ${DatabaseHelper.COLUMN_FAVORITES} = ?", arrayOf("1")).also { cursorFavorite = it }
        val result = mutableListOf<Film>()
        if (cursorFavorite.moveToFirst()) {
            do {
                val title = cursorFavorite.getString(1)
                val poster = cursorFavorite.getString(2)
                val description = cursorFavorite.getString(3)
                val rating = cursorFavorite.getDouble(4)
                val isFavorites = cursorFavorite.getInt(5)
                result.add(Film(title, poster, description, rating, IntToBoolean(isFavorites)))
            } while (cursorFavorite.moveToNext())
        }
        cursorFavorite.close()
        return result
    }

    private fun booleanToInt(boolean: Boolean): Int {
        return if (boolean) 1
        else 0
    }

    private fun IntToBoolean(number: Int): Boolean {
        return number == 0
    }
}
package com.example.findfilms.com.example.findfilms.di.module

import android.content.Context
import androidx.room.Room
import com.example.findfilms.data.dao.FavoritesFilmDao
import com.example.findfilms.data.repositories.MainRepository
import com.example.findfilms.data.dao.FilmDao
import com.example.findfilms.data.db.AppDatabase
import com.example.findfilms.data.db.FavoriteFilmsDatabase
import com.example.findfilms.data.repositories.FavoriteRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideFilmDao(context: Context) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "film_db").build().filmDao()

    @Provides
    @Singleton
    fun provideRepository(filmDao: FilmDao) = MainRepository(filmDao)

}
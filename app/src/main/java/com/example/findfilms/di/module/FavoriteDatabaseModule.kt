package com.example.findfilms.di.module

import android.content.Context
import androidx.room.Room
import com.example.findfilms.data.dao.FavoritesFilmDao
import com.example.findfilms.data.db.FavoriteFilmsDatabase
import com.example.findfilms.data.repositories.FavoriteRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FavoriteDatabaseModule {

    @Provides
    @Singleton
    fun provideFavoriteFilmDao(context: Context) = Room.databaseBuilder(
        context,
        FavoriteFilmsDatabase::class.java,
        "favorites_film_db").build().favoritesFilmsDao()

    @Provides
    @Singleton
    fun provideFavoritesFilmRepository(favoritesFilmDao: FavoritesFilmDao) = FavoriteRepository(favoritesFilmDao)
}
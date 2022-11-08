package com.example.findfilms.viewmodel

import androidx.lifecycle.ViewModel
import com.example.findfilms.App
import com.example.findfilms.data.Entity.FavoritesFilm
import com.example.findfilms.data.Entity.Film
import com.example.findfilms.domain.Interactor
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class FavoritesFragmentViewModel: ViewModel() {
    val filmsListData: Observable<List<Film>>
    @Inject
    lateinit var interactor: Interactor
    init {
        App.instance.dagger.inject(this)
        filmsListData = interactor.getFavoritesFilms()
    }
    fun getFavoritesFilms() {
        interactor.getFavoritesFilms()

    }

}
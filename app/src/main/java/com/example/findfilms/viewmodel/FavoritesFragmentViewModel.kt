package com.example.findfilms.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.findfilms.App
import com.example.findfilms.domain.Film
import com.example.findfilms.domain.Interactor
import javax.inject.Inject

class FavoritesFragmentViewModel : ViewModel() {
    val favoritesFilmListLiveData : MutableLiveData<List<Film>> = MutableLiveData()
    @Inject
    lateinit var interactor: Interactor
    init {
        App.instance.dagger.inject(this)
        favoritesFilmListLiveData.postValue(interactor.getFavoritesFilms())
    }

}
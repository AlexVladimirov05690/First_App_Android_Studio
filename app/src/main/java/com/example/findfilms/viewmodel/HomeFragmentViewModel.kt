package com.example.findfilms.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.findfilms.App
import com.example.findfilms.domain.Film
import com.example.findfilms.domain.Interactor

class HomeFragmentViewModel: ViewModel() {
    val filmsListLiveData: MutableLiveData<List<Film>> = MutableLiveData()
    private var interactor: Interactor = App.instance.interactor
    init {
        val films = interactor.getFilmsDB()
        filmsListLiveData.postValue(films)

    }
}
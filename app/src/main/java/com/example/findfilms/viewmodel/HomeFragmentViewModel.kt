package com.example.findfilms.viewmodel


import androidx.lifecycle.ViewModel
import com.example.findfilms.App
import com.example.findfilms.R
import com.example.findfilms.data.entity.Film
import com.example.findfilms.domain.Interactor
import com.example.findfilms.utils.SingleLiveEvent
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

class HomeFragmentViewModel : ViewModel() {
    val networkError = SingleLiveEvent<Int>()
    val showProgressBar: BehaviorSubject<Boolean>
    val filmsListData: Observable<List<Film>>
    @Inject
    lateinit var interactor: Interactor
    init {
        App.instance.dagger.inject(this)
        filmsListData = interactor.getFilmsFromDB()
        showProgressBar = interactor.progressBarState
        getFilms()
    }

    fun getFilms() {
        interactor.getFilmsFromApi(1)
    }

    fun networkErrorShow() {
        networkError.postValue(R.string.network_error)
    }

}


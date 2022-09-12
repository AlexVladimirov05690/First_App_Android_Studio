package com.example.findfilms.viewmodel

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.findfilms.App
import com.example.findfilms.R
import com.example.findfilms.data.Entity.Film
import com.example.findfilms.domain.Interactor
import com.example.findfilms.utils.SingleLiveEvent
import java.util.*
import java.util.concurrent.Executors
import javax.inject.Inject

class HomeFragmentViewModel : ViewModel() {
    val networkError = SingleLiveEvent<Int>()
    val showProgressBar: MutableLiveData<Boolean> = MutableLiveData()
    val filmsListLiveData: LiveData<List<Film>>
    @Inject
    lateinit var interactor: Interactor
    init {
        App.instance.dagger.inject(this)
        filmsListLiveData = interactor.getFilmsFromDB()
        getFilms()
    }

    fun getFilms() {
        showProgressBar.postValue(true)
        interactor.getFilmsFromApi(1, object : ApiCallback {
            override fun onSuccess() {
                showProgressBar.postValue(false)
            }

            override fun onFailure() {
                Executors.newSingleThreadExecutor().execute{
                    showProgressBar.postValue(false)
                    networkErrorShow()
                }
            }
        })
    }

    fun networkErrorShow() {
        networkError.postValue(R.string.network_error)
    }

    interface ApiCallback {
        fun onSuccess()
        fun onFailure()
    }
}


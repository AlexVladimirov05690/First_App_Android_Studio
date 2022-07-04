package com.example.findfilms

import android.app.Application
import com.example.findfilms.data.MainRepository
import com.example.findfilms.domain.Interactor

class App: Application() {
    lateinit var repo: MainRepository
    lateinit var interactor: Interactor

    override fun onCreate() {
        super.onCreate()
        instance = this
        repo = MainRepository()
        interactor = Interactor(repo)
    }

    companion object {
        lateinit var instance: App
            private set
    }
}
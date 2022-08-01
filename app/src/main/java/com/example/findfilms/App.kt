package com.example.findfilms

import android.app.Application
import com.example.findfilms.com.example.findfilms.di.AppComponent
import com.example.findfilms.com.example.findfilms.di.DaggerAppComponent


class App : Application() {
    lateinit var dagger: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        dagger = DaggerAppComponent.create()


    }

    companion object {
        lateinit var instance: App
            private set
    }
}
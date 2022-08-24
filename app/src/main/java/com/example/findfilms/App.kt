package com.example.findfilms

import android.app.Application
import com.example.findfilms.com.example.findfilms.di.AppComponent
import com.example.findfilms.com.example.findfilms.di.DaggerAppComponent
import com.example.findfilms.com.example.findfilms.di.module.DatabaseModule
import com.example.findfilms.com.example.findfilms.di.module.DomainModule
import com.example.findfilms.com.example.findfilms.di.module.RemoteModule


class App : Application() {
    lateinit var dagger: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        dagger = DaggerAppComponent.builder()
            .remoteModule(RemoteModule())
            .databaseModule(DatabaseModule())
            .domainModule(DomainModule(this))
            .build()


    }

    companion object {
        lateinit var instance: App
            private set
    }
}
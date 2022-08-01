package com.example.findfilms.com.example.findfilms.di

import com.example.findfilms.com.example.findfilms.di.module.DatabaseModule
import com.example.findfilms.com.example.findfilms.di.module.DomainModule
import com.example.findfilms.com.example.findfilms.di.module.RemoteModule
import com.example.findfilms.viewmodel.HomeFragmentViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RemoteModule::class,
        DomainModule::class,
        DatabaseModule::class

    ]
)
interface AppComponent {
    fun inject(homeFragmentViewModel: HomeFragmentViewModel)
}
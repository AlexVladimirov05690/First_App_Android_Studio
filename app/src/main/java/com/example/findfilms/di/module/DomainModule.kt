package com.example.findfilms.com.example.findfilms.di.module

import com.example.findfilms.BuildConfig
import com.example.findfilms.com.example.findfilms.data.ApiConstants
import com.example.findfilms.com.example.findfilms.data.TmdbApi
import com.example.findfilms.data.MainRepository
import com.example.findfilms.domain.Interactor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class DomainModule {
    @Singleton
    @Provides
    fun provideInteractor(repository: MainRepository, tmdbApi: TmdbApi) = Interactor(repo = repository, retrofitService = tmdbApi)
}
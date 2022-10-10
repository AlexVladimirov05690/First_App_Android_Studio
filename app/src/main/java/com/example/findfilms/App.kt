package com.example.findfilms

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.example.findfilms.com.example.findfilms.data.ApiConstants
import com.example.findfilms.com.example.findfilms.data.TmdbApi
import com.example.findfilms.com.example.findfilms.view.notifications.NotificationHelper
import com.example.findfilms.data.MainRepository
import com.example.findfilms.domain.Interactor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class App: Application() {
    lateinit var repo: MainRepository
    lateinit var interactor: Interactor
    lateinit var retrofitService: TmdbApi

    override fun onCreate() {
        super.onCreate()
        instance = this
        retrofitService = retrofit.create(TmdbApi::class.java)
        repo = MainRepository()
        interactor = Interactor(repo, retrofitService)
        createChannelNotifications()

    }

    companion object {
        lateinit var instance: App
            private set
    }

    private val okHttpClient = OkHttpClient.Builder()
        .callTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            if(BuildConfig.DEBUG) {
                level = HttpLoggingInterceptor.Level.BASIC
            }
        })
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(ApiConstants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    private fun createChannelNotifications() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "WatchLaterChannel"
            val descriptionText = R.string.notification_watch_later_desc
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channelNotification = NotificationChannel(NotificationHelper.CHANNEL_ID, name, importance)
            channelNotification.description = descriptionText.toString()
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channelNotification)
        }
    }
}
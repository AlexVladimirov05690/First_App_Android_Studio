package com.example.findfilms

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.example.findfilms.com.example.findfilms.di.AppComponent
import com.example.findfilms.com.example.findfilms.di.DaggerAppComponent
import com.example.findfilms.com.example.findfilms.di.module.DatabaseModule
import com.example.findfilms.com.example.findfilms.di.module.DomainModule
import com.example.findfilms.com.example.findfilms.di.module.RemoteModule
import com.example.findfilms.view.notification.NotificationHelper
import com.example.findfilms.view.notification.NotificationsConstants

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
        createChannelNotifications()
    }

    companion object {
        lateinit var instance: App
            private set
    }


    private fun createChannelNotifications() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "WatchLaterChannel"
            val descriptionText = R.string.notification_watch_later_desc
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channelNotification =
                NotificationChannel(NotificationsConstants.CHANNEL_ID, name, importance)
            channelNotification.description = descriptionText.toString()
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channelNotification)
        }
    }
}
package com.example.findfilms.utils.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.findfilms.data.Entity.Film
import com.example.findfilms.view.notification.NotificationHelper
import com.example.findfilms.view.notification.NotificationsConstants

class ReminderBroadcast : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val bundle = intent?.getBundleExtra(NotificationsConstants.FILM_BUNDLE_KEY)
        val film: Film = bundle?.get(NotificationsConstants.FILM_KEY) as Film
        NotificationHelper.createNotification(context!!, film)
    }
}
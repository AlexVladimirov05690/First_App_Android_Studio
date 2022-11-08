package com.example.findfilms.view.notification

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.findfilms.R
import com.example.findfilms.com.example.findfilms.data.ApiConstants
import com.example.findfilms.data.Entity.Film
import com.example.findfilms.view.MainActivity

object NotificationHelper {
    const val CHANNEL_ID = "FindFilmsChannel"

    fun createNotification(context: Context, film: Film) {
        val mIntent = Intent(context, MainActivity::class.java)
        mIntent.putExtra("film", film)
        val pendingIntent =
            PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_ONE_SHOT)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_watch_later)
            setContentTitle("Помнишь, ты хотел посмотреть")
            setContentText(film.title)
            priority = NotificationCompat.PRIORITY_DEFAULT
            setContentIntent(pendingIntent)
            setAutoCancel(true)
        }
        val notificationManager = NotificationManagerCompat.from(context)
        Glide.with(context)
            .asBitmap()
            .load(ApiConstants.IMAGES_URL + "w500" + film.poster)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    builder.setStyle(NotificationCompat.BigPictureStyle().bigPicture(resource))
                    notificationManager.notify(film.id, builder.build())
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }

            })
        notificationManager.notify(film.id, builder.build())

    }

}
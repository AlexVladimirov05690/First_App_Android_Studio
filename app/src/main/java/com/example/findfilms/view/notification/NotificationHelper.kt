package com.example.findfilms.view.notification

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.findfilms.R
import com.example.findfilms.com.example.findfilms.data.ApiConstants
import com.example.findfilms.data.Entity.Film
import com.example.findfilms.utils.receivers.ReminderBroadcast
import com.example.findfilms.view.MainActivity
import java.util.*

object NotificationHelper {

    fun createNotification(context: Context, film: Film) {
        val mIntent = Intent(context, MainActivity::class.java)
        mIntent.putExtra("film", film)
        val pendingIntent =
            PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val builder =
            NotificationCompat.Builder(context!!, NotificationsConstants.CHANNEL_ID).apply {
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

    fun notificationSet(context: Context, film: Film) {
        val calendar = Calendar.getInstance()
        val selectedYear = calendar.get(Calendar.YEAR)
        val selectedMonth = calendar.get(Calendar.MONTH)
        val selectedDay = calendar.get(Calendar.DATE)
        val selectedHour = calendar.get(Calendar.HOUR)
        val selectedMinute = calendar.get(Calendar.MINUTE)

        DatePickerDialog(
            context,
            { _, dpdYear, dpdMonth, dpdDayOfMonth ->
                val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    val pikedDateTime = Calendar.getInstance()
                    pikedDateTime.set(
                        dpdYear,
                        dpdMonth,
                        dpdDayOfMonth,
                        hourOfDay,
                        minute,
                        0
                    )
                    val dateTimeMillSec = pikedDateTime.timeInMillis
                    createWatchLater(context, dateTimeMillSec, film)
                }
                TimePickerDialog(
                    context,
                    timeSetListener,
                    selectedHour,
                    selectedMinute,
                    true
                ).show()
            },
            selectedYear,
            selectedMonth,
            selectedDay
        ).show()
    }

    private fun createWatchLater(context: Context, dateTimeMills: Long, film: Film) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(film.title, null, context, ReminderBroadcast()::class.java)
        val bundle = Bundle()
        bundle.putParcelable(NotificationsConstants.FILM_KEY, film)
        intent.putExtra(NotificationsConstants.FILM_BUNDLE_KEY, bundle)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            dateTimeMills,
            pendingIntent
        )

    }
}

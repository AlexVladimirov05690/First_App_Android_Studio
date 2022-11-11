package com.example.findfilms.data

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.core.content.edit
import com.example.findfilms.utils.DeclinationOfTimeValues
import java.util.*

class PreferenceProvider(context: Context) {
    private val appContext = context.applicationContext
    private val preference: SharedPreferences =
        appContext.getSharedPreferences("setting", Context.MODE_PRIVATE)

    init {
        preference.edit { putInt(KEY_TIMEOUT_CLEAR_DB, TIMEOUT_CLEAR_DB_MINUTE) }
        if (preference.getBoolean(KEY_FIRST_LAUNCH, true)) {
            val timeFirstLaunch = Calendar.getInstance()
            preference.edit { putString(KEY_DEFAULT_CATEGORY, DEFAULT_CATEGORY) }
            preference.edit { putLong(FIRST_RUN_TIME, timeFirstLaunch.timeInMillis) }
            preference.edit {
                putString(FIRST_RUN_TIME_STRING,
                    DeclinationOfTimeValues.timeInString(timeFirstLaunch.timeInMillis))
            }
            preference.edit { putBoolean(KEY_FIRST_LAUNCH, false) }
        }
    }


    fun saveDefaultCategory(category: String) {

        preference.edit { putString(KEY_DEFAULT_CATEGORY, category) }
    }

    fun getDefaultCategory(): String {
        return preference.getString(KEY_DEFAULT_CATEGORY, DEFAULT_CATEGORY) ?: DEFAULT_CATEGORY
    }


    fun checkTrialPeriod(): Boolean {
        val timeNowRun = Calendar.getInstance().timeInMillis
        val timeTrialPeriod = (TIMEOUT_TRIAL_PERIOD_DAYS * MILLSEC_IN_DAY).toLong()
        val result = timeNowRun - preference.getLong(FIRST_RUN_TIME, 0)
        if (result < timeTrialPeriod) {
            Toast.makeText(appContext,
                "До конца пробного периода осталось: ${DeclinationOfTimeValues.timeInString(timeTrialPeriod - result)}",
                Toast.LENGTH_SHORT).show()
        }
        return result < timeTrialPeriod
    }


    companion object {
        private const val KEY_FIRST_LAUNCH = "first_launch"
        private const val KEY_TIMEOUT_CLEAR_DB = "key_time"
        private const val KEY_DEFAULT_CATEGORY = "default_category"
        private const val DEFAULT_CATEGORY = "popular"
        private const val TIMEOUT_CLEAR_DB_MINUTE = 10
        private const val TIMEOUT_TRIAL_PERIOD_DAYS = 13
        private const val FIRST_RUN_TIME = "time run promo-period"
        private const val FIRST_RUN_TIME_STRING = "time run promo-period in format String"
        private const val MILLSEC_IN_DAY = 86400000
        private const val MILLSEC_IN_YEAR = 31536000000


    }
}


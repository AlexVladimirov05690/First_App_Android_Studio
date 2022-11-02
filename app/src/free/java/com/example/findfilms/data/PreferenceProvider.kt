package com.example.findfilms.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class PreferenceProvider(context: Context) {
    private val appContext = context.applicationContext
    private val preference: SharedPreferences =
        appContext.getSharedPreferences("setting", Context.MODE_PRIVATE)

    init {
        preference.edit { putInt(KEY_TIMEOUT_CLEAR_DB, TIMEOUT_CLEAR_DB_MINUTE)}
        if (preference.getBoolean(KEY_FIRST_LAUNCH, false)) {
            preference.edit { putString(KEY_DEFAULT_CATEGORY, DEFAULT_CATEGORY) }
            preference.edit { putBoolean(KEY_FIRST_LAUNCH, false) }
        }
    }


    fun saveDefaultCategory(category: String) {

        preference.edit { putString(KEY_DEFAULT_CATEGORY, category) }
    }

    fun getDefaultCategory(): String {
        return preference.getString(KEY_DEFAULT_CATEGORY, DEFAULT_CATEGORY) ?: DEFAULT_CATEGORY
    }

    fun timeForClearDb(timePrev: Long): Boolean {
        val timeNow = System.currentTimeMillis()
        return true
    }


    companion object {
        private const val KEY_FIRST_LAUNCH = "first_launch"
        private const val KEY_TIMEOUT_CLEAR_DB = "key_time"
        private const val KEY_DEFAULT_CATEGORY = "default_category"
        private const val DEFAULT_CATEGORY = "popular"
        private const val TIMEOUT_CLEAR_DB_MINUTE = 10
    }
}
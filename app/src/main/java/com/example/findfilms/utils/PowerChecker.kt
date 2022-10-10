package com.example.findfilms.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.findfilms.R

class PowerChecker(): BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent == null) return
        when (intent.action) {
            Intent.ACTION_BATTERY_LOW -> Toast.makeText(context, R.string.battery_low, Toast.LENGTH_SHORT).show()
            Intent.ACTION_POWER_CONNECTED -> Toast.makeText(context, R.string.power_connect, Toast.LENGTH_SHORT).show()
        }
    }
}
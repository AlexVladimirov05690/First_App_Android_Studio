package com.example.findfilms.utils

import android.app.Activity
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator
import java.util.concurrent.Executors
import kotlin.math.hypot
import kotlin.math.roundToInt

object AnimationHelper {
    private const val MENU_ITEMS = 5
    private const val DURATION = 500L
    private const val START_ANIMATION_CENTER = 0
    private const val WIDTH_CALCULATION_HELPER = 2
    private const val POSITION_CALCULATION_HELPER = 1


    fun performFragmentCircularRevealAnimation(rootView: View, activity: Activity, position: Int) {
        Executors.newSingleThreadExecutor().execute {
            while (true) {
                if (rootView.isAttachedToWindow) {
                    activity.runOnUiThread {
                        val itemCenter = rootView.width / (MENU_ITEMS * WIDTH_CALCULATION_HELPER)
                        val step = (itemCenter * WIDTH_CALCULATION_HELPER) * (position - POSITION_CALCULATION_HELPER) + itemCenter
                        val x: Int = step
                        val y: Int = rootView.y.roundToInt() + rootView.height
                        val startRadius = START_ANIMATION_CENTER
                        val endRadius = hypot(rootView.width.toDouble(), rootView.height.toDouble())
                        ViewAnimationUtils.createCircularReveal(rootView, x, y, startRadius.toFloat(), endRadius.toFloat()).apply {
                            duration = DURATION
                            interpolator = AccelerateDecelerateInterpolator()
                            start()
                        }
                        rootView.visibility = View.VISIBLE
                    }
                    return@execute
                }
            }
        }
    }
}
package com.example.findfilms.utils


object DeclinationOfTimeValues {
    private const val MILLSEC_IN_SEC = 1000
    private const val MILLSEC_IN_MIN = 60000
    private const val MILLSEC_IN_HOUR = 3600000
    private const val MILLSEC_IN_DAY = 86400000

    fun timeInString(date: Long): String {
        var dateTemp = date
        val day = dateTemp / MILLSEC_IN_DAY
        dateTemp -= day * MILLSEC_IN_DAY
        val hour = dateTemp / MILLSEC_IN_HOUR
        dateTemp -= hour * MILLSEC_IN_HOUR
        val minute = dateTemp / MILLSEC_IN_MIN
        dateTemp -= minute * MILLSEC_IN_MIN
        val sec = dateTemp / MILLSEC_IN_SEC
        return "$day ${dayInString(day)} $hour ${hoursInString(hour)} $minute ${
            minuteInString(minute)
        } $sec ${secInString(sec)}"
    }

    private fun dayInString(number: Long): String {
        if (number in 11..19) return "дней"
        else {
            val lastNumberChar = number.toString().toCharArray().last()
            return when (lastNumberChar.digitToInt()) {
                1 -> return "день"
                2, 3, 4 -> "дня"
                else -> return "дней"
            }
        }
    }

    private fun hoursInString(number: Long): String {
        return if (number in 11..19) "дней"
        else {
            val lastNumberChar = number.toString().toCharArray().last()
            when (lastNumberChar.digitToInt()) {
                1 -> "час"
                2, 3, 4 -> "часа"
                else -> "часов"
            }
        }
    }

    private fun minuteInString(number: Long): String {
        return if (number in 11..19) "минут"
        else {
            val lastNumberChar = number.toString().toCharArray().last()
            when (lastNumberChar.digitToInt()) {
                1 -> "минута"
                2, 3, 4 -> "минуты"
                else -> "минут"
            }
        }
    }

    private fun secInString(number: Long): String {
        return if (number in 11..19) "секунд"
        else {
            val lastNumberChar = number.toString().toCharArray().last()
            when (lastNumberChar.digitToInt()) {
                1 -> "секунда"
                2, 3, 4 -> "секунды"
                else -> "секунд"
            }
        }
    }
}
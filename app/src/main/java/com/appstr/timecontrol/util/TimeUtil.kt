package com.appstr.timecontrol.util

import java.util.concurrent.TimeUnit


fun Int.formatTimeToText(): String {

    val time = this.toLong()

    return when {

        this > hour -1 -> String.format("%2d:%02d:%02d",
            TimeUnit.MILLISECONDS.toHours(time),
            TimeUnit.MILLISECONDS.toMinutes(time) % TimeUnit.HOURS.toMinutes(1),
            TimeUnit.MILLISECONDS.toSeconds(time) % TimeUnit.MINUTES.toSeconds(1))

        this > minute -1 -> String.format("%2d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(time) % TimeUnit.HOURS.toMinutes(1),
            TimeUnit.MILLISECONDS.toSeconds(time) % TimeUnit.MINUTES.toSeconds(1))

        else -> String.format("%2d",
            TimeUnit.MILLISECONDS.toSeconds(time) % TimeUnit.MINUTES.toSeconds(1))

    }

}

fun Int.hoursFrom(): String = (this / hour).toString()

fun Int.minutesFrom(): String = ((this / minute) % 60).toString()

fun Int.secondsFrom(): String = ((this / second) % 60).toString()

fun String.isValidHours(): Boolean = when {
    this.any { !it.isDigit() } -> false
    this.length > 2 -> false
    (this.toIntOrNull() ?: 0) < 0 -> false
    else -> true
}

fun String.isValidMinutes(): Boolean = when {
    this.any { !it.isDigit() } -> false
    this.length > 2 -> false
    (this.toIntOrNull() ?: 0) < 0 -> false
    (this.toIntOrNull() ?: 0) > 59 -> false
    else -> true
}

fun String.isValidSeconds(): Boolean = when {
    this.any { !it.isDigit() } -> false
    this.length > 2 -> false
    (this.toIntOrNull() ?: 0) < 0 -> false
    (this.toIntOrNull() ?: 0) > 59 -> false
    else -> true
}

fun timeISValid(hrs: String, mins: String, secs: String): Boolean
    = (hrs.toIntOrNull() ?: 0) > 0 || (mins.toIntOrNull() ?: 0) > 0 || (secs.toIntOrNull() ?: 0) > 0

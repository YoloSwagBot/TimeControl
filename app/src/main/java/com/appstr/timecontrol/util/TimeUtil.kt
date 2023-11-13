package com.appstr.timecontrol.util


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

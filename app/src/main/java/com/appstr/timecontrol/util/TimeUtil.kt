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
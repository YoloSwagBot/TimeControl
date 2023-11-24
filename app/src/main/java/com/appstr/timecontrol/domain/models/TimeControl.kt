package com.appstr.timecontrol.domain.models

import com.appstr.timecontrol.util.hour
import com.appstr.timecontrol.util.minute
import com.appstr.timecontrol.util.second

data class TimeControl(
    val startValue: Int = 10*minute,
    val increment: Int = 5
)

/**
 * The string that represents the TimeControl is created based off whether
 * the startValue is >= to 1 hour || >= 1 minute else interpret as seconds;
 * Plus the increment in seconds.
 *
 * ie: input = TimeControl(12220000, 30)
 *    output = "3 hr 23 min 40 sec | 30 sec"
 */
fun TimeControl.toText(): String {
    var timeStr = ""

    val hrs = (startValue / hour)
    if (hrs > 0){
        timeStr += startValue/hour
        timeStr += " hr "
    }

    val mins = ((startValue / minute) % 60)
    if (mins > 0){
        timeStr += mins
        timeStr += " min "
    }

    val secs = ((startValue / second) % 60)
    if (secs > 0){
        timeStr += secs
        timeStr += " sec "
    }

    return timeStr + increment.toIncrementString()
}


private fun Int.toIncrementString(): String =
    when (this){
        0 -> ""
        else -> "| $this sec"
    }


package com.appstr.timecontrol.util

import com.appstr.timecontrol.hour
import com.appstr.timecontrol.minute
import com.appstr.timecontrol.second
import java.text.SimpleDateFormat
import java.util.Date



fun Int.formatTimeToText(): String {
    
    val sdf: SimpleDateFormat = when {
        this > hour-1 -> SimpleDateFormat("h:mm:ss")
        this > minute-1 -> SimpleDateFormat("m:ss")
        this >= 9 * second -> SimpleDateFormat("ss")
        else -> SimpleDateFormat("s")
    }

    return sdf.format(Date(this.toLong()))
}
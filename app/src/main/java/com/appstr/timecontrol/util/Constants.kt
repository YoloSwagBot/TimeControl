package com.appstr.timecontrol.util

import com.appstr.timecontrol.domain.models.TimeControl


const val second = 1000
const val minute = 60 * second
const val hour = 60 * minute

val defaultSelectedItem: Int = 6 // 10 mins + 5
val defaultTimeControls = listOf<TimeControl>(
    TimeControl(minute, 0), // bullet 1+0
    TimeControl(2* minute, 1), // bullet 2+1
    TimeControl(3* minute, 0), // blitz 3+0
    TimeControl(3* minute, 2), // blitz 3+2
    TimeControl(5* minute, 0), // blitz 5+0
    TimeControl(5* minute, 3), // blitz 5+3
    TimeControl(10* minute, 0), // rapid 10+0
    TimeControl(10* minute, 5), // rapid 10+5        <---- pos: 7
    TimeControl(15* minute, 10), // rapid 15+10
    TimeControl(30* minute, 0), // classical 30+0
    TimeControl(30* minute, 20), // classical 30+20
)


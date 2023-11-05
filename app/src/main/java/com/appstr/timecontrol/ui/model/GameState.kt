package com.appstr.timecontrol.ui.model

import com.appstr.timecontrol.minute


data class GameState (

    val timeControl: TimeControl = TimeControl(10*minute, 0),

    var turn: Int = 1, // 1 or 2
    var isPaused: Boolean = true,

    val player1StartTime: Int = timeControl.startValue,
    val player2StartTime: Int = timeControl.startValue,

    var player1CurrentTime: Int = timeControl.startValue,
    var player2CurrentTime: Int = timeControl.startValue,

    var player1MoveCount: Int = 0,
    var player2MoveCount: Int = 0

)


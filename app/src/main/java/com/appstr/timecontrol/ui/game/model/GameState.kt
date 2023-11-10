package com.appstr.timecontrol.ui.game.model

import com.appstr.timecontrol.util.second


data class GameState (

    val timeControl: TimeControl = TimeControl(10* second, 0),

    var turn: Int = 1, // 1 or 2
    var isPaused: Boolean = true,
    var isGameOver: Boolean = false,
    var gameEndReason: GameEndReason? = null,

    val player1StartTime: Int = timeControl.startValue,
    val player2StartTime: Int = timeControl.startValue,

    var player1CurrentTime: Int = timeControl.startValue,
    var player2CurrentTime: Int = timeControl.startValue,

    var player1MoveCount: Int = 0,
    var player2MoveCount: Int = 0

)

sealed class GameEndReason(reason: String){
    object CHECK_MATE: GameEndReason("Checkmate")
    object RAN_OUT_OF_TIME: GameEndReason("Ran out of time")
    object FORFEIT: GameEndReason("Resign")
    object UNKNOWN: GameEndReason("Unknown")
}

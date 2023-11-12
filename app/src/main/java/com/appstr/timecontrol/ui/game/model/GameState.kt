package com.appstr.timecontrol.ui.game.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.appstr.timecontrol.util.second


@Entity(tableName = "table_games")
data class GameState (

    @PrimaryKey val id: Int = 1,

    val timeControl: TimeControl = TimeControl(10* second, 0),

    var turn: Player = Player.ONE,
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

@Entity
sealed class GameEndReason(reason: String){
    data object CHECK_MATE: GameEndReason("Checkmate")
    data object RAN_OUT_OF_TIME: GameEndReason("Ran out of time")
    data object FORFEIT: GameEndReason("Resign")
    data object UNKNOWN: GameEndReason("Unknown")
}

@Entity
sealed class Player {
    data object ONE: Player()
    data object TWO: Player()
}
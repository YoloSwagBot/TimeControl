package com.appstr.timecontrol.ui.game.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "table_games")
data class GameState (

    @PrimaryKey val id: Int = 1,

    val timeControl: TimeControl? = null,

    var turn: Player = Player.ONE,
    var isPaused: Boolean = true,
    var gameEndReason: GameEndReason? = null,

    val player1StartTime: Int = timeControl?.startValue ?: -1,
    val player2StartTime: Int = timeControl?.startValue ?: -1,

    var player1CurrentTime: Int = timeControl?.startValue ?: -1,
    var player2CurrentTime: Int = timeControl?.startValue ?: -1,

    var player1MoveCount: Int = 0,
    var player2MoveCount: Int = 0

)

sealed class GameEndReason {
    data object CHECK_MATE: GameEndReason()
    data object STALEMATE: GameEndReason()
    data object RAN_OUT_OF_TIME: GameEndReason()
    data object RESIGN: GameEndReason()
    data object DRAW: GameEndReason()
    data object UNKNOWN: GameEndReason()
}

sealed class Player {
    data object ONE: Player()
    data object TWO: Player()
}

fun GameState?.canStart(): Boolean = this?.let { it.timeControl != null } ?: false

fun GameState?.isOver(): Boolean = this?.let { it.gameEndReason != null } ?: false
fun GameState?.isNotOver(): Boolean = this?.let { it.gameEndReason == null } ?: true

fun GameState?.areBothTimesAboveZero(): Boolean = this?.let { it.player1CurrentTime > 0 && it.player2CurrentTime > 0 } ?: false

package com.appstr.timecontrol.ui.game.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.appstr.timecontrol.util.hour
import com.appstr.timecontrol.util.minute
import java.util.concurrent.TimeUnit


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

// 12 reasons
sealed class GameEndReason {
    sealed class Checkmate: GameEndReason() {
        data object Checkmate_PLAYER_1_WINS: Checkmate()
        data object Checkmate_PLAYER_2_WINS: Checkmate()
    }
    sealed class RanOutOfTime: GameEndReason(){
        data object RanOutOfTime_PLAYER_1_WINS: Checkmate()
        data object RanOutOfTime_PLAYER_2_WINS: Checkmate()
    }
    sealed class Resignation: GameEndReason(){
        data object Resignation_PLAYER_1_WINS: Checkmate()
        data object Resignation_PLAYER_2_WINS: Checkmate()
    }
    sealed class Draw: GameEndReason(){
        data object Draw_INITIATED_BY_PLAYER_1: Draw()
        data object Draw_INITIATED_BY_PLAYER_2: Draw()
        data object Draw_REPETITION_BY_PLAYER_1: Draw()
        data object Draw_REPETITION_BY_PLAYER_2: Draw()
    }
    data object STALEMATE: GameEndReason()
    data object UNKNOWN: GameEndReason()
}

sealed class Player {
    data object ONE: Player()
    data object TWO: Player()
}

fun GameState?.doesntExist(): Boolean = this == null || this.timeControl == null
fun GameState?.exists(): Boolean = this != null && this.timeControl != null

fun GameState?.canStart(): Boolean = this.isNotOver() && this?.let { it.timeControl != null } ?: false

fun GameState?.isOver(): Boolean = this?.let { it.gameEndReason != null } ?: false
fun GameState?.isNotOver(): Boolean = this?.let { it.gameEndReason == null } ?: true

fun GameState?.areBothTimesAboveZero(): Boolean = this?.let { it.player1CurrentTime > 0 && it.player2CurrentTime > 0 } ?: false

val allEndGameReasons: List<GameEndReason> = listOf(
    GameEndReason.Checkmate.Checkmate_PLAYER_1_WINS,
    GameEndReason.Checkmate.Checkmate_PLAYER_2_WINS,
    GameEndReason.RanOutOfTime.RanOutOfTime_PLAYER_1_WINS,
    GameEndReason.RanOutOfTime.RanOutOfTime_PLAYER_2_WINS,
    GameEndReason.Resignation.Resignation_PLAYER_1_WINS,
    GameEndReason.Resignation.Resignation_PLAYER_2_WINS,
    GameEndReason.Draw.Draw_INITIATED_BY_PLAYER_1,
    GameEndReason.Draw.Draw_INITIATED_BY_PLAYER_2,
    GameEndReason.Draw.Draw_REPETITION_BY_PLAYER_1,
    GameEndReason.Draw.Draw_REPETITION_BY_PLAYER_2,
    GameEndReason.STALEMATE,
    GameEndReason.UNKNOWN
)
fun GameEndReason.toLabel(): String = when(this){
    GameEndReason.Checkmate.Checkmate_PLAYER_1_WINS -> "Checkmate: Player 1 wins."
    GameEndReason.Checkmate.Checkmate_PLAYER_2_WINS -> "Checkmate: Player 2 wins."
    GameEndReason.RanOutOfTime.RanOutOfTime_PLAYER_1_WINS -> "Player 2 ran out of time. Player 1 wins."
    GameEndReason.RanOutOfTime.RanOutOfTime_PLAYER_2_WINS -> "Player 1 ran out of time. Player 2 wins."
    GameEndReason.Resignation.Resignation_PLAYER_1_WINS -> "Player 2 resigned. Player 1 wins."
    GameEndReason.Resignation.Resignation_PLAYER_2_WINS -> "Player 1 resigned. Player 2 wins."
    GameEndReason.Draw.Draw_INITIATED_BY_PLAYER_1 -> "Draw. Player 1 offered. Player 2 accepted."
    GameEndReason.Draw.Draw_INITIATED_BY_PLAYER_2 -> "Draw. Player 2 offered. Player 1 accepted."
    GameEndReason.Draw.Draw_REPETITION_BY_PLAYER_1 -> "Player 1 forced Draw by repetition."
    GameEndReason.Draw.Draw_REPETITION_BY_PLAYER_2 -> "Player 2 forced Draw by repetition."
    GameEndReason.STALEMATE -> "Stalemate"
    GameEndReason.UNKNOWN -> "Unknown"
}
fun GameEndReason.toExplanation(): String = "Game Over: " + when(this){
    GameEndReason.Checkmate.Checkmate_PLAYER_1_WINS -> "Checkmate: Player 1 wins."
    GameEndReason.Checkmate.Checkmate_PLAYER_2_WINS -> "Checkmate: Player 2 wins."
    GameEndReason.RanOutOfTime.RanOutOfTime_PLAYER_1_WINS -> "Player 2 ran out of time. Player 1 wins."
    GameEndReason.RanOutOfTime.RanOutOfTime_PLAYER_2_WINS -> "Player 1 ran out of time. Player 2 wins."
    GameEndReason.Resignation.Resignation_PLAYER_1_WINS -> "Player 2 resigned. Player 1 wins."
    GameEndReason.Resignation.Resignation_PLAYER_2_WINS -> "Player 1 resigned. Player 2 wins."
    GameEndReason.Draw.Draw_INITIATED_BY_PLAYER_1 -> "Draw. Player 1 offered. Player 2 accepted."
    GameEndReason.Draw.Draw_INITIATED_BY_PLAYER_2 -> "Draw. Player 2 offered. Player 1 accepted."
    GameEndReason.Draw.Draw_REPETITION_BY_PLAYER_1 -> "Player 1 forced Draw by repetition."
    GameEndReason.Draw.Draw_REPETITION_BY_PLAYER_2 -> "Player 2 forced Draw by repetition."
    GameEndReason.STALEMATE -> "Stalemate"
    GameEndReason.UNKNOWN -> "Unknown"
}


fun Int?.formatTimeToText(): String {
    if (this == null || this < 0) return "?"
    val time = this.toLong()

    return when {

        this > hour -1 -> String.format("%2d:%02d:%02d",
            TimeUnit.MILLISECONDS.toHours(time),
            TimeUnit.MILLISECONDS.toMinutes(time) % TimeUnit.HOURS.toMinutes(1),
            TimeUnit.MILLISECONDS.toSeconds(time) % TimeUnit.MINUTES.toSeconds(1))

        this > minute -1 -> String.format("%2d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(time) % TimeUnit.HOURS.toMinutes(1),
            TimeUnit.MILLISECONDS.toSeconds(time) % TimeUnit.MINUTES.toSeconds(1))

        else -> String.format("0:%02d",
            TimeUnit.MILLISECONDS.toSeconds(time) % TimeUnit.MINUTES.toSeconds(1))

    }

}

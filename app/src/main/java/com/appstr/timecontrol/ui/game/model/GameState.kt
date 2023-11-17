package com.appstr.timecontrol.ui.game.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.appstr.timecontrol.util.hour
import com.appstr.timecontrol.util.minute
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@Entity(tableName = "table_games")
data class GameState @Inject constructor (

    @PrimaryKey val id: Int = 1,

    @Inject val timeControl: TimeControl? = null,

    @Inject var turn: Player = Player.ONE,
    @Inject var isPaused: Boolean = true,

    @Inject val player1StartTime: Int = timeControl?.startValue ?: -1,
    @Inject val player2StartTime: Int = timeControl?.startValue ?: -1,

    @Inject var player1CurrentTime: Int = timeControl?.startValue ?: -1,
    @Inject var player2CurrentTime: Int = timeControl?.startValue ?: -1,

    @Inject var player1MoveCount: Int = 0,
    @Inject var player2MoveCount: Int = 0

)


sealed class Player {
    data object ONE: Player()
    data object TWO: Player()
}

fun GameState?.doesntExist(): Boolean = this == null || this.timeControl == null
fun GameState?.exists(): Boolean = this != null && this.timeControl != null

fun GameState?.canStart(): Boolean = this.isNotOver() && this?.let { it.timeControl != null } ?: false

fun GameState?.isOver(): Boolean = this?.let { !it.areBothTimesAboveZero() } ?: false
fun GameState?.isNotOver(): Boolean = this?.isOver() == false

fun GameState?.areBothTimesAboveZero(): Boolean = this?.let { it.player1CurrentTime > 0 && it.player2CurrentTime > 0 } ?: false



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

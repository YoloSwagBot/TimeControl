package com.appstr.timecontrol.domain.data

import androidx.room.TypeConverter
import com.appstr.timecontrol.ui.game.model.GameEndReason
import com.appstr.timecontrol.ui.game.model.Player
import com.appstr.timecontrol.ui.game.model.TimeControl
import com.google.gson.Gson

object Converters {

    @TypeConverter
    fun timeControlToString(timeControl: TimeControl): String = Gson().toJson(timeControl)
    @TypeConverter
    fun stringToTimeControl(timeControlJson: String): TimeControl = Gson().fromJson(timeControlJson, TimeControl::class.java)


    @TypeConverter
    fun playerToString(player: Player): String = when (player){
        Player.ONE -> "1"
        Player.TWO -> "2"
    }
    @TypeConverter
    fun stringToPlayer(player: String): Player = when (player){
        "1" -> Player.ONE
        else -> Player.TWO
    }


    @TypeConverter
    fun gameEndReasonToString(gameEndReason: GameEndReason): String = when (gameEndReason) {
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
    @TypeConverter
    fun stringToGameEndReason(gameEndReason: String): GameEndReason = when (gameEndReason){
        "Checkmate: Player 1 wins." -> GameEndReason.Checkmate.Checkmate_PLAYER_1_WINS
        "Checkmate: Player 2 wins." -> GameEndReason.Checkmate.Checkmate_PLAYER_2_WINS
        "Player 2 ran out of time. Player 1 wins." -> GameEndReason.RanOutOfTime.RanOutOfTime_PLAYER_1_WINS
        "Player 1 ran out of time. Player 2 wins." -> GameEndReason.RanOutOfTime.RanOutOfTime_PLAYER_2_WINS
        "Player 2 resigned. Player 1 wins." -> GameEndReason.Resignation.Resignation_PLAYER_1_WINS
        "Player 1 resigned. Player 2 wins." -> GameEndReason.Resignation.Resignation_PLAYER_2_WINS
        "Draw. Player 1 offered. Player 2 accepted." -> GameEndReason.Draw.Draw_INITIATED_BY_PLAYER_1
        "Draw. Player 2 offered. Player 1 accepted." -> GameEndReason.Draw.Draw_INITIATED_BY_PLAYER_2
        "Player 1 forced Draw by repetition." -> GameEndReason.Draw.Draw_REPETITION_BY_PLAYER_1
        "Player 2 forced Draw by repetition." -> GameEndReason.Draw.Draw_REPETITION_BY_PLAYER_2
        "Stalemate" -> GameEndReason.STALEMATE
        else -> GameEndReason.UNKNOWN
    }



}
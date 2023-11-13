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
        GameEndReason.CHECK_MATE -> "CHECK_MATE"
        GameEndReason.RAN_OUT_OF_TIME -> "RAN_OUT_OF_TIME"
        GameEndReason.RESIGN -> "RESIGN"
        GameEndReason.STALEMATE -> "STALEMATE"
        GameEndReason.DRAW -> "DRAW"
        GameEndReason.UNKNOWN -> "UNKNOWN"
    }
    @TypeConverter
    fun stringToGameEndReason(gameEndReason: String): GameEndReason = when (gameEndReason){
        "CHECK_MATE" -> GameEndReason.CHECK_MATE
        "RAN_OUT_OF_TIME" -> GameEndReason.RAN_OUT_OF_TIME
        "RESIGN" -> GameEndReason.RESIGN
        "STALEMATE" -> GameEndReason.STALEMATE
        "DRAW" -> GameEndReason.DRAW
        else -> GameEndReason.UNKNOWN
    }



}
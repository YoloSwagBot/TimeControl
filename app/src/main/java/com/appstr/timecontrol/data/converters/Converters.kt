package com.appstr.timecontrol.data.converters

import androidx.room.TypeConverter
import com.appstr.timecontrol.domain.models.Player
import com.appstr.timecontrol.domain.models.TimeControl
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

}

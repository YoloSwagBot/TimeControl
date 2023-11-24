package com.appstr.timecontrol.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.appstr.timecontrol.data.converters.Converters
import com.appstr.timecontrol.data.daos.GameStateDao
import com.appstr.timecontrol.domain.models.GameState
import javax.inject.Singleton


@Database(entities = [GameState::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
@Singleton
abstract class GameStateDatabase: RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "game_database"
    }

    abstract fun gameDao(): GameStateDao

}

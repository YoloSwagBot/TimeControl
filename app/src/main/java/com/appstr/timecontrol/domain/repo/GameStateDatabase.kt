package com.appstr.timecontrol.domain.repo

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.appstr.timecontrol.ui.game.model.GameState
import javax.inject.Singleton


@Database(entities = [GameState::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
@Singleton
abstract class GameStateDatabase: RoomDatabase() {

    abstract fun gameDao(): GameStateDao

    companion object {
        const val DATABASE_NAME = "game_database"
    }

}

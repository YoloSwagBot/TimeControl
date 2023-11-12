package com.appstr.timecontrol.domain.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.appstr.timecontrol.ui.game.model.GameState
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized


@Database(entities = [GameState::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class GameDatabase(): RoomDatabase() {

    abstract fun gameDao(): GameDao

    companion object {

        private const val DATABASE_NAME = "game_database"


        @Volatile
        private var INSTANCE: GameDatabase? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getDatabase(appliContext: Context): GameDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    appliContext,
                    GameDatabase::class.java,
                    DATABASE_NAME)
                        .build()

                INSTANCE = instance

                instance
            }
        }

    }

}

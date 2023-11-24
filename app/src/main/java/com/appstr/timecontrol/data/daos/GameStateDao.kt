package com.appstr.timecontrol.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.appstr.timecontrol.domain.models.GameState


@Dao
interface GameStateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun putGameState(gameState: GameState)

    @Query("SELECT * FROM table_games WHERE id == 1")
    suspend fun getGameState(): GameState?


}


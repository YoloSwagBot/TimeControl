package com.appstr.timecontrol.domain.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.appstr.timecontrol.ui.game.model.GameState


@Dao
interface GameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun putGameState(gameState: GameState)

    @Query("SELECT * FROM table_games WHERE id == 1")
    suspend fun getGameState(): GameState?


}


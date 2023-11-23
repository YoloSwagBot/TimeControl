package com.appstr.timecontrol.domain.repo

import androidx.annotation.WorkerThread
import com.appstr.timecontrol.ui.game.model.GameState
import javax.inject.Inject

class GameStateRepository @Inject constructor(private val gameStateDao: GameStateDao) {


    suspend fun getGameState(): GameState? = gameStateDao.getGameState()

    @WorkerThread
    suspend fun updateGameState(gameState: GameState){
        gameStateDao.putGameState(gameState)
    }

}
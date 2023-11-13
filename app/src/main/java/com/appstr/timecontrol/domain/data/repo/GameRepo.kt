package com.appstr.timecontrol.domain.data.repo

import androidx.annotation.WorkerThread
import com.appstr.timecontrol.domain.data.GameDao
import com.appstr.timecontrol.ui.game.model.GameState

class GameRepo(private val gameDao: GameDao) {


    suspend fun getGameState(): GameState? = gameDao.getGameState()

    @WorkerThread
    suspend fun updateGameState(gameState: GameState){
        gameDao.putGameState(gameState)
    }

}
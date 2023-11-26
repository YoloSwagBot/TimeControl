package com.appstr.timecontrol.data.repositories

import androidx.annotation.WorkerThread
import com.appstr.timecontrol.data.daos.GameStateDao
import com.appstr.timecontrol.domain.interfaces.GameStateRepoIF
import com.appstr.timecontrol.domain.models.GameState
import javax.inject.Inject

open class GameStateRepository @Inject constructor(private val gameStateDao: GameStateDao) : GameStateRepoIF {

    override suspend fun getGameState(): GameState? = gameStateDao.getGameState()

    @WorkerThread
    override suspend fun updateGameState(gameState: GameState) = gameStateDao.putGameState(gameState)

    override suspend fun deleteAllGames() = gameStateDao.deleteAllGames()

}
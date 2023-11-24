package com.appstr.timecontrol.domain.interfaces

import com.appstr.timecontrol.domain.models.GameState

interface GameStateRepoIF {

    suspend fun getGameState(): GameState?

    suspend fun updateGameState(gameState: GameState)

}
package com.appstr.timecontrol.domain.usecases.gamescreen

import com.appstr.timecontrol.domain.models.GameState
import com.appstr.timecontrol.domain.models.Player
import com.appstr.timecontrol.util.second
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class DecrementTimeUseCase @Inject constructor() {
    operator fun invoke(
        _gameState: MutableStateFlow<GameState?>,
        gs: GameState?
    ){
        gs?.let { game ->
            _gameState.update {
                when (game.turn){
                    Player.ONE -> {
                        game.copy(
                            player1CurrentTime = (game.player1CurrentTime - second).coerceAtLeast(0),
                            isPaused = game.player1CurrentTime <= 1
                        )
                    }
                    Player.TWO -> {
                        game.copy(
                            player2CurrentTime = (game.player2CurrentTime - second).coerceAtLeast(0),
                            isPaused = game.player2CurrentTime <= 1
                        )
                    }
                }
            }
        }
    }
}